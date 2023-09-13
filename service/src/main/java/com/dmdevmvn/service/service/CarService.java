package com.dmdevmvn.service.service;

import com.dmdevmvn.service.dao.CarRepository;
import com.dmdevmvn.service.dto.CarReadDto;
import com.dmdevmvn.service.dto.CarCreateDto;
import com.dmdevmvn.service.entity.Car;
import com.dmdevmvn.service.mapper.CarCreateMapper;
import com.dmdevmvn.service.mapper.CarReadMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarReadMapper carReadMapper;
    private final CarCreateMapper carCreateMapper;

    public Long create(CarCreateDto carDto) {
        var car = carCreateMapper.mapFrom(carDto);
        return carRepository.save(car).getId();
    }

    public Optional<CarReadDto> findById(Long id) {
        var carRootGraph = carRepository.getEntityManager().createEntityGraph(Car.class);
        carRootGraph.addAttributeNodes("client");

        Map<String, Object> properties = Map.of(
                GraphSemantic.FETCH.getJpaHintName(), carRootGraph
        );
        return carRepository.findById(id, properties)
                .map(carReadMapper::mapFrom);
    }

    public boolean delete(Long id) {
        var maybeCar = carRepository.findById(id);
        maybeCar.ifPresent(carRepository::delete);
        return maybeCar.isPresent();
    }
}
