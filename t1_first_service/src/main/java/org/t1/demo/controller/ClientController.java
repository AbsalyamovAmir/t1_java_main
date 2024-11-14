package org.t1.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.t1.demo.kafka.KafkaClientProducer;
import org.t1.demo.model.Client;
import org.t1.demo.model.dto.ClientDto;
import org.t1.demo.repository.ClientRepository;
import org.t1.demo.service.ClientService;
import org.t1.demo.util.ClientMapper;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;
    private final KafkaClientProducer kafkaClientProducer;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @PostMapping(value = "/generate")
    public void generateClient() {
        clientService.generateClients();
    }

    @GetMapping("/register")
    public ResponseEntity<Client> register(@RequestBody ClientDto clientDto) {
        log.info("Registering client: {}", clientDto);
        Client client = clientService.registerClient(
                clientMapper.toEntityWithId(clientDto)
        );
//        log.info("Client registered: {}", client.getId());
        return ResponseEntity.ok().body(client);
    }
}
