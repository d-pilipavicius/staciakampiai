package com.example.demo.orderComponent.domain.entities;

import com.example.demo.serviceChargeComponent.domain.entities.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(
        name = "order_item_modifier",
        indexes = {
            @Index(name = "idx_order_item_modifier_order_item_id", columnList = "order_item_id"),
            @Index(name = "idx_order_item_modifier_product_modifier_id", columnList = "product_modifier_id")
        }
)
public class OrderItemModifier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JoinColumn(name = "order_item_id", nullable = false)
    private UUID orderItemId;

    @JoinColumn(name = "product_modifier_id", nullable = false)
    private UUID productModifierId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
