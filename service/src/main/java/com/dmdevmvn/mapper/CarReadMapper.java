package com.dmdevmvn.mapper;

import com.dmdevmvn.dto.CarReadDto;
import com.dmdevmvn.entity.Car;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CarReadMapper implements Mapper<Car, CarReadDto> {

    private final ClientReadMapper clientReadMapper;

    @Override
    public CarReadDto mapFrom(Car object) {
        return new CarReadDto(
                object.getId(),
                object.getModel(),
                object.getYear(),
                object.getMileage(),
                Optional.ofNullable(object.getClient())
                        .map(clientReadMapper::mapFrom)
                        .orElse(null)
        );
    }
}
