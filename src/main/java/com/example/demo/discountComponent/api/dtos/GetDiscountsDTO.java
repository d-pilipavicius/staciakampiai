package com.example.demo.discountComponent.api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class GetDiscountsDTO {
    @NotNull
    private final int totalItems;
    @NotNull
    private final int totalPages;
    @NotNull
    private final int currentPage;
    @NotNull
    private final List<@Valid DiscountDTO> items;
}
