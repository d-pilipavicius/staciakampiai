package com.example.demo.orders.API.DTOs.OrderDTOs;

import com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects.FullOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetOrderDTO {
    private FullOrder order;
}
