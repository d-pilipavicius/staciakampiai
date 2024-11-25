package com.example.demo.orders.domain.entities;

import com.example.demo.orders.domain.entities.enums.Currency;
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
        name = "order_item",
        indexes = {
            @Index(name = "idx_order_item_order_id", columnList = "order_id"),
            @Index(name = "idx_order_item_product_id", columnList = "product_id")
        }
)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<AppliedDiscount> appliedDiscounts;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<OrderItemTax> orderItemTaxes;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
