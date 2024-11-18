package com.example.demo.orders.API.DTOs.DiscountDTO;

import com.example.demo.orders.API.DTOs.DiscountDTO.DiscountDTOsObjects.FullDiscount;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetDiscountsDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<FullDiscount> items;
}
