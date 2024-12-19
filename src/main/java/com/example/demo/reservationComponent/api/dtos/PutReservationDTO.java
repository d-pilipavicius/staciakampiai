package com.example.demo.reservationComponent.api.dtos;


import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.CustomerDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.util.Optional;

@Getter
@Builder
public class PutReservationDTO {
    @NotNull
    private final Timestamp reservationStartAt;

    @NotNull
    private final Timestamp reservationEndAt;

    @NotNull
    private final CustomerDTO customer;
}
