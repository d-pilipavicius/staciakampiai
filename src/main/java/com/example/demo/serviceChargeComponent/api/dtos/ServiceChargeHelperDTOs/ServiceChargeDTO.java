package com.example.demo.serviceChargeComponent.api.dtos.ServiceChargeHelperDTOs;

import com.example.demo.serviceChargeComponent.domain.entities.enums.Currency;
import com.example.demo.serviceChargeComponent.domain.entities.enums.PricingStrategy;
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
public class ServiceChargeDTO {
    private UUID id;
    private String title;
    private PricingStrategy valueType;
    private BigDecimal serviceChargeValue;
    private Optional<Currency> currency;
    private UUID businessId;
}
