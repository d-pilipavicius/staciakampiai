package com.example.demo.orders.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "reservation",
        indexes = {
                @Index(name = "idx_reservation_business_id", columnList = "business_id"),
                @Index(name = "idx_reservation_customer_id", columnList = "customer_id"),
                @Index(name = "idx_reservation_employee_id", columnList = "employee_id"),
                @Index(name = "idx_reservation_order_id", columnList = "order_id")
        }
)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<ReservationNotification> reservationNotifications;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<AppliedServiceCharge> appliedServiceCharges;

    @Column(nullable = false)
    private Timestamp reservationStartAt;

    @Column(nullable = false)
    private Timestamp reservationEndAt;
}
