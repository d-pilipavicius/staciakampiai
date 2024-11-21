package com.example.demo.orders.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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

    @JoinColumn(name = "business_id", nullable = false)
    private UUID businessId;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    @Column(name = "employee_id", nullable = true)
    private UUID employeeId;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<ReservationNotification> reservationNotifications;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<AppliedServiceCharge> appliedServiceCharges;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp reservationStartAt;

    @Column(nullable = false)
    private Timestamp reservationEndAt;
}
