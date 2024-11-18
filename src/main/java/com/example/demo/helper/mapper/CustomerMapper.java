package com.example.demo.helper.mapper;

import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.orders.API.DTOs.ReservationDTOs.ReservationDTOsObjects.CustomerDTO;
import com.example.demo.orders.domain.entities.Customer;

public class CustomerMapper {

    public static final StaticMapper<CustomerDTO, Customer> TO_MODEL = dto -> {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPhoneNumber(dto.getPhoneNumber());
        return customer;
    };

    public static final StaticMapper<Customer, CustomerDTO> TO_DTO = entity -> {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(entity.getFirstName());
        customerDTO.setLastName(entity.getLastName());
        customerDTO.setPhoneNumber(entity.getPhoneNumber());
        return customerDTO;
    };
}
