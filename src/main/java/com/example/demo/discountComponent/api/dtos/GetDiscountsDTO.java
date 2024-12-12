package com.example.demo.discountComponent.api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class GetDiscountsDTO {
    private final int totalItems;
    private final int totalPages;
    private final int currentPage;
    @NotNull
    private final List<@Valid DiscountDTO> items;
}
