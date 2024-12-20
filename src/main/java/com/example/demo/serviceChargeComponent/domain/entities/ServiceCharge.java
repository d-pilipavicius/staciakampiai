package com.example.demo.serviceChargeComponent.domain.entities;

import com.example.demo.CommonHelper.enums.Currency;
import com.example.demo.CommonHelper.enums.PricingStrategy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_charge" /*
                                * ,
                                * indexes = {
                                * 
                                * @Index(name = "idx_service_charge_business_id", columnList = "business_id")
                                * }
                                */
)
public class ServiceCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JoinColumn(name = "business_id", nullable = false)
    private UUID businessId;

    @Column(nullable = false)
    private String title;

    @Column(name = "service_charge_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal serviceChargeValue;

    @Column(name = "value_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PricingStrategy valueType;

    @Column(nullable = true, length = 3)
    @Enumerated(EnumType.STRING)
    private Currency currency;

}
