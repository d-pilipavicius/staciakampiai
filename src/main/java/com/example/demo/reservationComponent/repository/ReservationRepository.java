package com.example.demo.reservationComponent.repository;

import com.example.demo.reservationComponent.domain.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    Page<Reservation> findAllByBusinessId(UUID businessId, Pageable pageable);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.customer.firstName = :firstName " +
            "AND r.customer.lastName = :lastName " +
            "AND r.reservationStartAt > CURRENT_TIMESTAMP")
    Page<Reservation> findAllByCustomerFullName(String firstName, String lastName, Pageable pageable);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.businessId = :businessId " +
            "AND r.reservationStartAt < :endAt " +
            "AND r.reservationEndAt > :startAt " +
            "AND r.id != :id")
    List<Reservation> findOverlappingReservationsForPut(
            @Param("businessId") UUID businessId,
            @Param("startAt") Timestamp startAt,
            @Param("endAt") Timestamp endAt,
            @Param("id") UUID id
    );

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.businessId = :businessId " +
            "AND r.reservationStartAt < :endAt " +
            "AND r.reservationEndAt > :startAt")
    List<Reservation> findOverlappingReservationsForPut(
            @Param("businessId") UUID businessId,
            @Param("startAt") Timestamp startAt,
            @Param("endAt") Timestamp endAt
    );
}
