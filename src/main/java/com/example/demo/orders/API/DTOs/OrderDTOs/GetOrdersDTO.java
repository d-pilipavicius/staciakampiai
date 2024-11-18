package com.example.demo.orders.API.DTOs.OrderDTOs;

import com.example.demo.orders.API.DTOs.BaseDTOs.GetBaseDTO;
import com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects.FullOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersDTO extends GetBaseDTO {
    private List<FullOrder> items;
}
