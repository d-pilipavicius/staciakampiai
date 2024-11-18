package com.example.demo.orders.api.DTOs.ReservationDTOs.ReservationDTOsObjects;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class FullReservation {
    private UUID id;
    private CustomerDTO customerDTO;
    private UUID createdByEmployeeId;
    private Timestamp createdAt;
    private Timestamp reservationStartAt;
    private Timestamp reservationEndAt;
    private UUID businessId;
}
