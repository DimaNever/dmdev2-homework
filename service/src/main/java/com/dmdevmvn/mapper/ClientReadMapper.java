package com.dmdevmvn.mapper;

import com.dmdevmvn.dto.ClientReadDto;
import com.dmdevmvn.entity.Client;

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
