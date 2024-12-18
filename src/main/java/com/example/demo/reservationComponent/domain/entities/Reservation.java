package com.example.demo.reservationComponent.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(
        name = "reservation",
        indexes = {
                @Index(name = "idx_reservation_business_id", columnList = "business_id"),
                @Index(name = "idx_reservation_employee_id", columnList = "employee_id"),
        }
)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JoinColumn(name = "business_id", nullable = false)
    private UUID businessId;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "reservation_start_at", nullable = false)
    private Timestamp reservationStartAt;

    @Column(name = "reservation_end_at", nullable = false)
    private Timestamp reservationEndAt;

    @ElementCollection
    @CollectionTable(
            name = "reservation_service_charge_ids",
            joinColumns = @JoinColumn(name = "reservation_id")
    )
    @Column(name = "service_charge_id", nullable = false)
    private List<UUID> serviceChargeIds = new ArrayList<>();
}
