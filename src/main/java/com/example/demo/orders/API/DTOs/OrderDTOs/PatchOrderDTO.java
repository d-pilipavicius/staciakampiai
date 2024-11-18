package com.example.demo.orders.API.DTOs.OrderDTOs;

import com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects.SelectedProduct;
import com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects.SelectedDiscount;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class PatchOrderDTO {
    private Optional<List<SelectedProduct>> items;
    private Optional<List<SelectedDiscount>> discounts;
    private Optional<List<UUID>> serviceChargeIds;
    private Optional<UUID> businessId;
}
