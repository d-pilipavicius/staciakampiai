package com.example.demo.reservationComponent.api.dtos;


import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.CustomerDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Builder
public class PostReservationDTO {
    @NotNull
    private final CustomerDTO customer;

    @NotNull
    private final Timestamp reservationStartAt;

    @NotNull
    private final Timestamp reservationEndAt;

    private final List<UUID> serviceChargeIds;

    @NotNull
    private final UUID businessId;
}
