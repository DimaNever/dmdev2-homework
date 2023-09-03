package com.dmdevmvn.service.dto;

public record ClientReadDto(Long id,
                            String firstName,
                            String lastName,
                            Long phoneNumber) {
}
