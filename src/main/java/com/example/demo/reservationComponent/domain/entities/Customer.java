package com.example.demo.reservationComponent.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(
        name = "customer",
        indexes = {
            @Index(name = "idx_customer_reservation_id", columnList = "reservation_id")
        }
)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "reservation_id", nullable = false)
    private UUID reservationId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;
}
