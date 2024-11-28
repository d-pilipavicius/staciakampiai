package com.example.demo.reservationComponent.api.dtos;


import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.CustomerDTO;
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
public class PostReservationDTO {
    private CustomerDTO customer;
    private Timestamp reservationStartAt;
    private Timestamp reservationEndAt;
    private Optional<List<UUID>> serviceChargeIds;
    private UUID businessId;
}
