package com.dmdevmvn.service.mapper;

import com.dmdevmvn.service.dao.ClientRepository;
import com.dmdevmvn.service.dto.CarCreateDto;
import com.dmdevmvn.service.entity.Car;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CarCreateMapper implements Mapper<CarCreateDto, Car> {

    private final ClientRepository clientRepository;

    @Override
    public Car mapFrom(CarCreateDto object) {
        return Car.builder()
                .model(object.model())
                .year(object.year())
                .mileage(object.mileage())
                .client(clientRepository.findByID(object.clientId()).orElseThrow(IllegalArgumentException::new))
                .build();
    }
}
