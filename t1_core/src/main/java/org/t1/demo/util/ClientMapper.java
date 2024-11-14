package org.t1.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.t1.demo.model.Client;
import org.t1.demo.model.dto.ClientDto;

@Component
@Slf4j
public class ClientMapper {

    public static Client toEntity(ClientDto dto) {
        if (dto.getMiddleName() == null) {
//            throw new NullPointerException();
        }
        return Client.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .clientId(dto.getClientId())
                .build();
    }

    public static ClientDto toDto(Client entity) {
        return ClientDto.builder()
//                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .clientId(entity.getClientId())
                .build();
    }

    public Client toEntityWithId(ClientDto dto) {
        log.info("Mapping to entity with id if exist");
        if (dto.getMiddleName() == null) {
            throw new NullPointerException();
        }
        return Client.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .build();
    }
}
