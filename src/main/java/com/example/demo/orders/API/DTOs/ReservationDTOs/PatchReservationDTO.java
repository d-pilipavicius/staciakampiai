package com.example.demo.orders.API.DTOs.ReservationDTOs;

import com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationDTOsObjects.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchReservationDTO {
    private Optional<Timestamp> reservationStartAt;
    private Optional<Timestamp> reservationEndAt;
    private Optional<Customer> customer;
}
