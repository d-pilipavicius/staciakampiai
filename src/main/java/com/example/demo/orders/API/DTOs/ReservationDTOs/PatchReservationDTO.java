package com.example.demo.orders.API.DTOs.ReservationDTOs;

import com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationDTOsObjects.CustomerDTO;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Optional;

@Getter
@Setter
public class PatchReservationDTO {
    private Optional<Timestamp> reservationStartAt;
    private Optional<Timestamp> reservationEndAt;
    private Optional<CustomerDTO> customer;
}
