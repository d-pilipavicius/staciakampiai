package com.example.demo.orders.API.DTOs.ServiceChargeDTOs.ServiceChargeDTOsObjects;

import com.example.demo.orders.API.DTOs.ServiceChargeDTOs.PostServiceChargeDTO;
import com.example.demo.orders.domain.entities.enums.Currency;
import com.example.demo.orders.domain.entities.enums.PricingStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullServiceCharge extends PostServiceChargeDTO {
    private UUID id;
}
