package com.example.demo.productComponent.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.demo.CommonHelper.enums.Currency;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "product_modifier", indexes = {
        @Index(name = "idx_product_modifier_business_id", columnList = "business_id")
})
public class ProductModifier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "business_id", nullable = false)
    private UUID businessId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private int quantityInStock;

    @Version
    @Column(nullable = false)
    private int rowVersion = 0;
}
