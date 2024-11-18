package com.example.demo.orders.API.DTOs.ReservationDTOs;

import com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationDTOsObjects.FullReservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPatchReturnReservationDTO {
    private FullReservation reservation;
}
