package com.example.demo.helper.mapper;

import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.orders.API.DTOs.ReservationDTOs.PostReservationDTO;
import com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.orders.domain.entities.Reservation;

public class ReservationMapper {

   /* public static final StaticMapper<PostReservationDTO, Reservation> TO_MODEL = dto -> {
        Reservation reservation = new Reservation();
        reservation.setReservationStartAt(dto.getReservationStartAt());
        reservation.setReservationEndAt(dto.getReservationEndAt());
        reservation.setBusinessId(dto.getBusinessId());
        reservation.setCustomer(
            Mapper.mapToModel(
                dto.getCustomerDTO(),
                CustomerMapper.TO_MODEL
            )
        );
        // TODO: employeeId is missing and createdAt is missing
        // TODO: wtf is serviceChargeIds? they should be created after reservation is created
        return reservation;
    };

    public static final StaticMapper<Reservation, FullReservation> TO_DTO = entity -> {
        FullReservation reservationDTO = new FullReservation();
        reservationDTO.setId(entity.getId());
        reservationDTO.setCustomerDTO(
            Mapper.mapToDTO(
                entity.getCustomer(),
                CustomerMapper.TO_DTO
            )
        );
        reservationDTO.setCreatedByEmployeeId(entity.getEmployeeId());
        reservationDTO.setReservationStartAt(entity.getReservationStartAt());
        reservationDTO.setReservationEndAt(entity.getReservationEndAt());
        reservationDTO.setCreatedAt(entity.getCreatedAt());
        reservationDTO.setBusinessId(entity.getBusinessId());
        return reservationDTO;
    };*/

}
