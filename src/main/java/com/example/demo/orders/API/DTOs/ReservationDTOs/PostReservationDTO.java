package com.example.demo.orders.API.DTOs.ReservationDTOs;


import com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationDTOsObjects.CustomerObject;
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
    private CustomerObject customer;
    private Timestamp reservationStartAt;
    private Timestamp reservationEndAt;
    private Optional<List<UUID>> serviceChargeIds;
    private UUID businessId;
}
