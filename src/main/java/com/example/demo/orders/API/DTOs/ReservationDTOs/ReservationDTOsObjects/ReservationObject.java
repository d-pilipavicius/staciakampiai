package com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationDTOsObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationObject {
    private UUID id;
    private CustomerObject customer;
    private UUID createdByEmployeeId;
    private Timestamp createdAt;
    private Timestamp reservationStartAt;
    private Timestamp reservationEndAt;
    private UUID businessId;
}
