package com.example.demo.productComponent.domain.entities;

import com.example.demo.orderComponent.domain.entities.OrderItem;
import com.example.demo.taxComponent.domain.entities.Tax;
import com.example.demo.serviceChargeComponent.domain.entities.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
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

    @ElementCollection
    @CollectionTable(name = "discount_entitled_product", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "discount_id")
    private List<UUID> discountIds;

    @ManyToMany
    @JoinTable(
            name = "product_tax",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tax_id")
    )
    private List<Tax> taxes;

    @ManyToMany
    @JoinTable(
            name = "product_modifier_entitled_product",
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
