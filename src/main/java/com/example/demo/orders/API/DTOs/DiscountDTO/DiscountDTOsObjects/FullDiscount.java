package com.example.demo.orders.API.DTOs.DiscountDTO.DiscountDTOsObjects;

import com.example.demo.orders.API.DTOs.DiscountDTO.PostDiscountDTO;
import com.example.demo.orders.domain.entities.enums.DiscountTarget;
import com.example.demo.orders.domain.entities.enums.PricingStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullDiscount extends PostDiscountDTO {
    private UUID id;
    private int usageCount;
}
