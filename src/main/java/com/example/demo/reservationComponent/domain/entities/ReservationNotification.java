package com.example.demo.reservationComponent.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "reservation_notification",
        indexes = {
                @Index(name = "idx_reservation_notification_reservation_id", columnList = "reservation_id")
        }
)
public class ReservationNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(nullable = false)
    private String text;

    @Column(nullable = true)
    private Timestamp sentAt;
}
