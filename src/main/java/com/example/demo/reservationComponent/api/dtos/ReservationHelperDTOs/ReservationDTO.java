package com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Builder
public class ReservationDTO {
    private final UUID id;
    private final CustomerDTO customer;
    private final UUID createdByEmployeeId;
    private final Timestamp createdAt;
    private final Timestamp reservationStartAt;
    private final Timestamp reservationEndAt;
    private final UUID businessId;
}
