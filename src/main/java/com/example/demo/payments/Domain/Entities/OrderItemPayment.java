package com.example.demo.payments.Domain.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item_payment")
public class OrderItemPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @Column(name = "payment_id", nullable = false)
    private UUID paymentId;

    @Column(name = "order_item_id", nullable = false)
    private UUID orderItemId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}

