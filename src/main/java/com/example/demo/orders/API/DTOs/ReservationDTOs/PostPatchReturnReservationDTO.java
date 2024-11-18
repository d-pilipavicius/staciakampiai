package com.example.demo.orders.API.DTOs.ReservationDTOs;


import com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationDTOsObjects.ReservationObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPatchReturnReservationDTO {
    private ReservationObject reservation;
}
