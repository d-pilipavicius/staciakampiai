package com.example.demo.orders.API.DTOs.ReservationDTOs;


import com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationHelperDTOs.ReservationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * A response reservation DTO for Patch and Post requests
 */
public class ResponseReservationDTO {
    private ReservationDTO reservation;
}
