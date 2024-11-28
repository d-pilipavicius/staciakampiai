package com.example.demo.orderComponent.api.dtos;

import com.example.demo.orderComponent.api.dtos.OrderHelperDTOs.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<OrderDTO> items;
}
