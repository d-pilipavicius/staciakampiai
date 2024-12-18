package com.example.demo.reservationComponent.repository;

import com.example.demo.reservationComponent.domain.entities.ReservationNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationNotificationRepository extends JpaRepository<ReservationNotification, UUID> {
}
