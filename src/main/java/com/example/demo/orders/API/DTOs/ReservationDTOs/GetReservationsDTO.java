package com.example.demo.orders.API.DTOs.ReservationDTOs;

import com.example.demo.orders.API.DTOs.BaseDTOs.GetBaseDTO;
import com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationDTOsObjects.FullReservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetReservationsDTO extends GetBaseDTO {
    private List<FullReservation> items;
}
