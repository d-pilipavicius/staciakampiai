package com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FullNameDTO {
    private final String firstName;
    private final String lastName;
}
