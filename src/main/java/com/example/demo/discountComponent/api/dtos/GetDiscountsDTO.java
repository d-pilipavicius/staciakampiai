package com.example.demo.discountComponent.api.dtos;

import com.example.demo.discountComponent.api.dtos.DiscountHelperDTOs.DiscountDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDiscountsDTO {
    @NotNull
    private int totalItems;
    @NotNull
    private int totalPages;
    @NotNull
    private int currentPage;
    @NotNull
    private List<DiscountDTO> items;
}
