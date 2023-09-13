package com.dmdevmvn.mapper;

import com.dmdevmvn.dao.ClientRepository;
import com.dmdevmvn.dto.CarCreateDto;
import com.dmdevmvn.entity.Car;
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
                .client(clientRepository.findById(object.clientId()).orElseThrow(IllegalArgumentException::new))
                .build();
    }
}
