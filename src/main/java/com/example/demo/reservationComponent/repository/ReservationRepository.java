package com.example.demo.reservationComponent.repository;

import com.example.demo.reservationComponent.domain.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    Page<Reservation> findAllByBusinessId(UUID businessId, Pageable pageable);
}
