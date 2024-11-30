package com.example.demo.productComponent.domain.entities;

import com.example.demo.helper.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(
        name = "product",
        indexes = {
            @Index(name = "idx_product_business_id", columnList = "business_id")
        }
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "business_id", nullable = false)
    private UUID businessId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int quantityInStock;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Version
    @Column(nullable = false)
    private byte[] rowVersion;
}
