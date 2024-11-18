package com.example.demo.orders.API.DTOs.DiscountDTO;

import com.example.demo.orders.domain.entities.enums.DiscountTarget;
import com.example.demo.orders.domain.entities.enums.PricingStrategy;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Getter
@Setter
public class PostDiscountDTO {
    private Optional<String> code;
    private BigDecimal value;
    private PricingStrategy valueType;
    private Optional<Currency> currency;
    private Timestamp validFrom;
    private Timestamp validUntil;
    private DiscountTarget target;
    private Optional<List<UUID>> entitledProductIds;
    private UUID businessId;
    private Optional<Integer> usageCountLimit;
}
