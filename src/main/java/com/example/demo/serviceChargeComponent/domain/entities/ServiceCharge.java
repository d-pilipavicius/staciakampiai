package com.example.demo.serviceChargeComponent.domain.entities;

import com.example.demo.serviceChargeComponent.domain.entities.enums.Currency;
import com.example.demo.serviceChargeComponent.domain.entities.enums.PricingStrategy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(
        name = "service_charge",
        indexes = {
            @Index(name = "idx_service_charge_business_id", columnList = "business_id")
        }
)
public class ServiceCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JoinColumn(name = "business_id", nullable = false)
    private UUID businessId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PricingStrategy valueType;

    @Column(nullable = true)
    private Currency currency;
}
