package com.example.demo.orders.API.DTOs.OrderDTOs;

import com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects.SelectedDiscount;
import com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects.SelectedProduct;
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
public class PostOrderDTO {
    private Optional<UUID> reservationId;
    private List<SelectedProduct> items;
    private List<SelectedDiscount> discounts;
    private List<UUID> serviceChargeIds;
    private UUID businessId;
}
