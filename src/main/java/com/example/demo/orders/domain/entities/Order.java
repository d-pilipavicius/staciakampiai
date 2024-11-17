package com.example.demo.orders.domain.entities;

import com.example.demo.orders.domain.entities.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "orders",
        indexes = {
                @Index(name = "idx_order_business_id_index", columnList = "business_id"),
                @Index(name = "idx_order_employee_id_index", columnList = "employee_id"),
        }
)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<Reservation> reservations;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<AppliedDiscount> appliedDiscounts;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<AppliedServiceCharge> appliedServiceCharges;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
