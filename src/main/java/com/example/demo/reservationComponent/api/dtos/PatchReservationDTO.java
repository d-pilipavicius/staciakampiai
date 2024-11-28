package com.example.demo.reservationComponent.api.dtos;


import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchReservationDTO {
    private Optional<Timestamp> reservationStartAt;
    private Optional<Timestamp> reservationEndAt;
    private Optional<CustomerDTO> customer;
}
