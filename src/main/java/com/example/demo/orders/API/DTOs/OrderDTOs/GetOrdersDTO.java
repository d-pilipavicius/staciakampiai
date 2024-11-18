package com.example.demo.orders.API.DTOs.OrderDTOs;

import com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects.FullOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetOrdersDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<FullOrder> items;
}
