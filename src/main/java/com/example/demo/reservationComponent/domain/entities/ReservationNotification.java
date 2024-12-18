package com.example.demo.reservationComponent.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(
        name = "reservation_notification",
        indexes = {
                @Index(name = "idx_reservation_notification_reservation_id", columnList = "reservation_id")
        }
)
public class ReservationNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "reservation_id", nullable = false)
    private UUID reservationId;

    @Column(nullable = false)
    private String text;

    @Column(name = "sent_at", nullable = true)
    private Timestamp sentAt;
}
