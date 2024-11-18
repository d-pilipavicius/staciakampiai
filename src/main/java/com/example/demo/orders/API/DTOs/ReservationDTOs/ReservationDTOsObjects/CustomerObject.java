package com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationDTOsObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerObject {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
