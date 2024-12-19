package com.example.demo.payments.API.DTOs;

import com.example.demo.serviceChargeComponent.api.dtos.ServiceChargeHelperDTOs.ServiceChargeDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetTipsDTO {
    private final int totalItems;
    private final int totalPages;
    private final int currentPage;

    @NotNull
    private List<@Valid TipDTO> items;
}