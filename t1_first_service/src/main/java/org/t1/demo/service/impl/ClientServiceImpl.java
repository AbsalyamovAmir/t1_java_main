package org.t1.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.t1.demo.exception.ClientException;
import org.t1.demo.kafka.KafkaClientProducer;
import org.t1.demo.model.Client;
import org.t1.demo.model.dto.ClientDto;
import org.t1.demo.repository.ClientRepository;
import org.t1.demo.service.ClientService;
import org.t1.demo.util.ClientMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository repository;
    private final KafkaClientProducer kafkaClientProducer;
//    private final CheckWebClient checkWebClient;

    @Override
    public List<Client> registerClients(List<Client> clients) {
        List<Client> savedClients = new ArrayList<>();
        for (Client client : clients) {
//            Optional<CheckResponse> check = checkWebClient.check(client.getId());
//            check.ifPresent(checkResponse -> {
//                if (!checkResponse.getBlocked()) {
//                    Client saved = repository.save(client);
//                    kafkaClientProducer.send(saved.getId());
//                    savedClients.add(saved);
//                }
//            });
            savedClients.add(repository.save(client));
        }

        return savedClients;
    }

    @Override
    public Client registerClient(Client client) {
        Client saved = null;
//        Optional<CheckResponse> check = checkWebClient.check(client.getId());
//        if (check.isPresent()) {
//            if (!check.get().getBlocked()) {
                saved = repository.save(client);
                kafkaClientProducer.send(client.getId());
//            }
//        }
        return saved;
    }

    @Override
    public List<ClientDto> parseJson() {
        log.info("Parsing json");
        ObjectMapper mapper = new ObjectMapper();
        ClientDto[] clients = new ClientDto[0];
        try {
//            clients = mapper.readValue(new File("src/main/resources/MOCK_DATA.json"), ClientDto[].class);
            clients = new ClientDto[]{ClientDto.builder()
                    .firstName("first_name_1")
                    .build(),
                    ClientDto.builder()
                            .firstName("first_name_2")
                            .build()};
        } catch (Exception e) {
//            throw new RuntimeException(e);
            log.warn("Exception: ", e);
        }
        log.info("Found {} clients", clients.length);
        return Arrays.asList(clients);
    }

    @Override
    public void clearMiddleName(List<ClientDto> dtos) {
        log.info("Clearing middle name");
        dtos.forEach(dto -> dto.setMiddleName(null));
        log.info("Done clearing middle name");
    }

    @Override
    public Client findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ClientException("Client with ID " + id + " not found"));
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public void generateClients() {
        List<ClientDto> clientsDto = parseJson();
        List<Client> clients = new ArrayList<>();
        for (ClientDto clientDto : clientsDto) {
            clients.add(ClientMapper.toEntity(clientDto));
        }
        repository.saveAll(clients);
    }
}
