package com.example.demo.reservationComponent.helper.mapper;

import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.reservationComponent.api.dtos.ReservationHelperDTOs.CustomerDTO;
import com.example.demo.reservationComponent.domain.entities.Customer;

public class CustomerMapper {

    public static final StaticMapper<CustomerDTO, Customer> TO_MODEL = dto -> Customer.builder()
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .phoneNumber(dto.getPhoneNumber())
            .build();

    public static final StaticMapper<Customer, CustomerDTO> TO_DTO = entity -> CustomerDTO.builder()
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .phoneNumber(entity.getPhoneNumber())
            .build();
}
