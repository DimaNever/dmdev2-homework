package com.dmdevmvn.dto;

public record ClientReadDto(Long id,
                            String firstName,
                            String lastName,
                            Long phoneNumber) {
}
