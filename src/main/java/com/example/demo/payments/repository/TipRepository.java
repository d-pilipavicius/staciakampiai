package com.example.demo.payments.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.payments.domain.entities.Tip;

 import java.util.UUID;

public interface TipRepository extends JpaRepository<Tip, UUID> {
    Page<Tip> findByOrderId(UUID orderId, Pageable pageable);
}