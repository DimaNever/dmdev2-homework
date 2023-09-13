package com.dmdevmvn.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarFilter {
    String firstName;
    String lastName;
}
