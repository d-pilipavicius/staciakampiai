package com.example.demo.reservationComponent.api.dtos;


import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.ReservationDTO;
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
