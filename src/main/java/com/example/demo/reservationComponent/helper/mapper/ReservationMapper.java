package com.example.demo.reservationComponent.helper.mapper;

import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.reservationComponent.api.dtos.PostReservationDTO;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.reservationComponent.domain.entities.Reservation;

import java.sql.Timestamp;

public class ReservationMapper {

    public static final StaticMapper<PostReservationDTO, Reservation> TO_MODEL = dto -> {
        Reservation reservation = new Reservation();
        reservation.setReservationStartAt(dto.getReservationStartAt());
        reservation.setReservationEndAt(dto.getReservationEndAt());
        reservation.setBusinessId(dto.getBusinessId());
        reservation.setCustomer(
            Mapper.mapToModel(
                dto.getCustomer(),
                CustomerMapper.TO_MODEL
            )
        );
        reservation.setCreatedAt(
                new Timestamp(System.currentTimeMillis())
        );
        // TODO: employeeId is missing and createdAt is missing -> should be set in the service
        // TODO: wtf is serviceChargeIds? they should be created after reservation is created
        return reservation;
    };

    public static final StaticMapper<Reservation, ReservationDTO> TO_DTO = entity -> {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(entity.getId());
        reservationDTO.setCustomer(
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
    };

}
