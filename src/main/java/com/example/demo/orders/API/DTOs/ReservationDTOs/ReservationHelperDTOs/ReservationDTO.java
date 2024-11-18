package com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationHelperDTOs;

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
public class ReservationDTO {
    private UUID id;
    private CustomerDTO customer;
    private UUID createdByEmployeeId;
    private Timestamp createdAt;
    private Timestamp reservationStartAt;
    private Timestamp reservationEndAt;
    private UUID businessId;
}
