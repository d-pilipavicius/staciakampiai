package com.example.demo.orders.domain.entities;

import com.example.demo.orders.domain.entities.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "product_modifier",
        indexes = {
            @Index(name = "idx_product_modifier_business_id", columnList = "business_id")
        }
)
public class ProductModifier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private BusinessMock business;

    @ManyToMany(mappedBy = "productModifiers")
    private List<Product> products;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private int quantityInStock;

    @Column(nullable = false)
    private byte[] rowVersion;
}
