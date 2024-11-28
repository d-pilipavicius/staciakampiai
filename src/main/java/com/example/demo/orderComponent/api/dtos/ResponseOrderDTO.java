package com.example.demo.orderComponent.api.dtos;

import com.example.demo.orderComponent.api.dtos.OrderHelperDTOs.OrderDTO;
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
