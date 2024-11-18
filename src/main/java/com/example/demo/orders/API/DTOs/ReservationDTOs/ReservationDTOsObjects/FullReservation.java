package com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationDTOsObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullReservation {
    private UUID id;
    private Customer customer;
    private UUID createdByEmployeeId;
    private Timestamp createdAt;
    private Timestamp reservationStartAt;
    private Timestamp reservationEndAt;
    private UUID businessId;
}
