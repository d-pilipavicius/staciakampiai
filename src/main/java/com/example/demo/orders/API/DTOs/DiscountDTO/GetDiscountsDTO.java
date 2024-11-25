package com.example.demo.orders.API.DTOs.DiscountDTO;

import com.example.demo.orders.API.DTOs.DiscountDTO.DiscountHelperDTOs.DiscountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetDiscountsDTO {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<DiscountDTO> items;

    public GetDiscountsDTO(List<DiscountDTO> items) {
        this.items = items;
    }
}
