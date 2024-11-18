package com.example.demo.orders.API.DTOs.ReservationDTOs;

import com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationDTOsObjects.CustomerDTO;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class PostReservationDTO {
    private CustomerDTO customerDTO;
    private Timestamp reservationStartAt;
    private Timestamp reservationEndAt;
    private Optional<List<UUID>> serviceChargeIds;
    private UUID businessId;
}
