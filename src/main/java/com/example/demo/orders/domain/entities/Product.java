package com.example.demo.orders.domain.entities;

import com.example.demo.orders.domain.entities.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private BusinessMock business;

    @ManyToMany(mappedBy = "products")
    private List<Discount> discounts;

    @ManyToMany
    @JoinTable(
            name = "product_tax",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tax_id")
    )
    private List<Tax> taxes;

    @ManyToMany
    @JoinTable(
            name = "product_compatible_modifier",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "product_modifier_id")
    )
    private List<ProductModifier> productModifiers;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<OrderItem> orderItems;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int quantityInStock;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private byte[] rowVersion;
}
