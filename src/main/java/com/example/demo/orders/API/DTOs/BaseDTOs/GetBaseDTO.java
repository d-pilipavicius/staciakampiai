package com.example.demo.orders.API.DTOs.BaseDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetBaseDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
