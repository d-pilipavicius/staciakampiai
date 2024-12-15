package com.example.demo.reservationComponent.helper.mapper;

import com.example.demo.CommonHelper.mapper.base.Mapper;
import com.example.demo.CommonHelper.mapper.base.StaticMapper;
import com.example.demo.reservationComponent.api.dtos.GetReservationsDTO;
import com.example.demo.reservationComponent.api.dtos.PostReservationDTO;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.ReservationDTO;
import com.example.demo.reservationComponent.domain.entities.Reservation;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;

public class ReservationMapper {

    public static final StaticMapper<PostReservationDTO, Reservation> TO_MODEL = dto -> Reservation.builder()
            .reservationStartAt(dto.getReservationStartAt())
            .reservationEndAt(dto.getReservationEndAt())
            .businessId(dto.getBusinessId())
            .createdAt(new Timestamp(System.currentTimeMillis()))
            .serviceChargeIds(dto.getServiceChargeIds())
            .customer(Mapper.mapToModel(dto.getCustomer(), CustomerMapper.TO_MODEL))
            .build();

    public static final StaticMapper<Reservation, ReservationDTO> TO_DTO = entity -> ReservationDTO.builder()
            .id(entity.getId())
            .customer(Mapper.mapToDTO(entity.getCustomer(), CustomerMapper.TO_DTO))
            .createdByEmployeeId(entity.getEmployeeId())
            .reservationStartAt(entity.getReservationStartAt())
            .reservationEndAt(entity.getReservationEndAt())
            .createdAt(entity.getCreatedAt())
            .businessId(entity.getBusinessId())
            .serviceChargeIds(entity.getServiceChargeIds())
            .build();

    public static final StaticMapper<Page<Reservation>, GetReservationsDTO> PAGE_TO_DTO = reservations -> GetReservationsDTO.builder()
            .currentPage(reservations.getPageable().getPageNumber())
            .totalPages(reservations.getTotalPages())
            .totalItems((int)reservations.getTotalElements())
            .items(Mapper.mapToDTOList(
                    reservations.getContent(),
                    ReservationMapper.TO_DTO)
            )
            .build();
}
