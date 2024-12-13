package com.example.demo.OrderComponent.Domain.Entities;

import com.example.demo.OrderComponent.Domain.Entities.Enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "business_id", nullable = false)
    private UUID businessId;

    @Column(name = "reservation_id")
    private UUID reservationId;

    @NotNull
    @Column(name = "created_by_employee_id", nullable = false)
    private UUID createdByEmployeeId;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}