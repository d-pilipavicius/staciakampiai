package com.example.demo.orders.API.DTOs.ProductDTOs;

import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierDTOsObjects.FullProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GetProductsDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<FullProduct> items;
    private UUID businessId;
}
