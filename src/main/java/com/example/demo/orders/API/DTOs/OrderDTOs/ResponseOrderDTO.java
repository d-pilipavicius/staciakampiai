package com.example.demo.orders.API.DTOs.OrderDTOs;

import com.example.demo.orders.API.DTOs.OrderDTOs.OrderHelperDTOs.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * A response order DTO for Patch, Post and Get (for singular order) requests
 */
public class ResponseOrderDTO {
    private OrderDTO order;
}
