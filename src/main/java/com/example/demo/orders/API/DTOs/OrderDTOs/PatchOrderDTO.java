package com.example.demo.orders.API.DTOs.OrderDTOs;

import com.example.demo.orders.API.DTOs.OrderDTOs.OrderHelperDTOs.SelectedProductDTO;
import com.example.demo.orders.API.DTOs.OrderDTOs.OrderHelperDTOs.SelectedDiscountDTO;
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
