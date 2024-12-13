package com.example.demo.OrderComponent.Domain.Entities;

import com.example.demo.helper.enums.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item_modifier")
public class OrderItemModifier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "order_item_id", nullable = false)
    private UUID orderItemId;

    @Column(name = "product_modifier_id", nullable = false)
    private UUID productModifierId;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

    @Column(name = "currency",nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
