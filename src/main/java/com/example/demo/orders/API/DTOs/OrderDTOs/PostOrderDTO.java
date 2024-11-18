package com.example.demo.orders.API.DTOs.OrderDTOs;

import com.example.demo.orders.API.DTOs.OrderDTOs.OrderHelperDTOs.SelectedDiscountDTO;
import com.example.demo.orders.API.DTOs.OrderDTOs.OrderHelperDTOs.SelectedProductDTO;
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
    private List<SelectedProductDTO> items;
    private List<SelectedDiscountDTO> discounts;
    private List<UUID> serviceChargeIds;
    private UUID businessId;
}
