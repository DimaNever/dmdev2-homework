package com.dmdevmvn.dto;

public record CarCreateDto(String model,
                           Integer year,
                           Long mileage,
                           Long clientId) {
}
