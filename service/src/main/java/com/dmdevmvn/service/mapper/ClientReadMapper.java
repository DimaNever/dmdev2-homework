package com.dmdevmvn.service.mapper;

import com.dmdevmvn.service.dto.ClientReadDto;
import com.dmdevmvn.service.entity.Client;

public class ClientReadMapper implements Mapper<Client, ClientReadDto> {
    @Override
    public ClientReadDto mapFrom(Client object) {
        return new ClientReadDto(
                object.getId(),
                object.getFirstName(),
                object.getLastName(),
                object.getPhoneNumber()
        );
    }
}
