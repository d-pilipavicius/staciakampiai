package com.example.demo.OrderComponent.Domain.Entities;

import com.example.demo.OrderComponent.Domain.Entities.Enums.Currency;
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

    @Column(name = "orderItemId", nullable = false)
    private UUID orderItemId;

    @Column(nullable = false)
    private UUID productModifierId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
