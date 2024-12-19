package com.example.demo.taxComponent.api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetTaxesDTO {
    private final int totalItems;
    private final int totalPages;
    private final int currentPage;

    @NotNull
    private final List<@Valid TaxDTO> items;

}
