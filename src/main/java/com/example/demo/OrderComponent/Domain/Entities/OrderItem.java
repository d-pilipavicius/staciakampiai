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
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @NonNull
    @Column(name = "product_id", nullable = false)
    private UUID productID;

    @NonNull
    @Column(name = "title", nullable = false)
    private String title;

    @NonNull
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @NonNull
    @Column(name = "UnitPrice", nullable = false)
    private BigDecimal unitPrice;

    @NonNull
    @Column(name = "currency", nullable = false)
    private Currency currency;
}
