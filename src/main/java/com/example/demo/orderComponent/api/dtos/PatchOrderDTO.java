package com.example.demo.orderComponent.api.dtos;

import com.example.demo.orderComponent.api.dtos.OrderHelperDTOs.SelectedProductDTO;
import com.example.demo.orderComponent.api.dtos.OrderHelperDTOs.SelectedDiscountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchOrderDTO {
    private Optional<List<SelectedProductDTO>> items;
    private Optional<List<SelectedDiscountDTO>> discounts;
    private Optional<List<UUID>> serviceChargeIds;
    private Optional<UUID> businessId;
}
