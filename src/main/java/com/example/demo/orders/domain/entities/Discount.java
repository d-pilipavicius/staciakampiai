package com.example.demo.orders.domain.entities;

import com.example.demo.orders.domain.entities.enums.Currency;
import com.example.demo.orders.domain.entities.enums.DiscountTarget;
import com.example.demo.orders.domain.entities.enums.PricingStrategy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "discount",
        indexes = {
                @Index(name = "idx_discount_business_id", columnList = "business_id"),
                @Index(name = "idx_discount_applied_discount_id", columnList = "applied_discount_id"),
        }
)
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToMany
    @JoinTable(
            name = "discount_entitled_product",
            joinColumns = @JoinColumn(name = "discount_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @OneToMany
    @JoinColumn(name = "applied_discount_id", nullable = true)
    private List<AppliedDiscount> appliedDiscounts;

    @Column(nullable = true)
    private String code;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PricingStrategy valueType;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountTarget target;

    @Column(nullable = false)
    private Timestamp ValidFrom;

    @Column(nullable = false)
    private Timestamp ValidUntil;

    @Column(nullable = true)
    private int usageCountLimit;
}
