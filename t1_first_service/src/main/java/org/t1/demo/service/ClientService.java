package org.t1.demo.service;

import org.t1.demo.model.Client;
import org.t1.demo.model.dto.ClientDto;

import java.util.List;

public interface ClientService {
    List<Client> registerClients(List<Client> clients);

    Client registerClient(Client client);

    List<ClientDto> parseJson();

    void clearMiddleName(List<ClientDto> dtos);

    Client findById(Long id);

    List<Client> findAll();

    void generateClients();
}
