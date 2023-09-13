package com.dmdevmvn.dto;

public record CarReadDto(Long id,
                         String model,
                         Integer year,
                         Long mileage,
                         ClientReadDto client) {
}
