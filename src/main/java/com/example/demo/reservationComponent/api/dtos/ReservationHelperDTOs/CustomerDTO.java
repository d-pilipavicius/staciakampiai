package com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs;

import lombok.*;

@Getter
@Builder
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
