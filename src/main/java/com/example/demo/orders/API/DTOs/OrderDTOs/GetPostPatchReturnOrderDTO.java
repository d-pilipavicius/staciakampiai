package com.example.demo.orders.API.DTOs.OrderDTOs;

import com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects.OrderObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPostPatchReturnOrderDTO {
    private OrderObject order;
}
