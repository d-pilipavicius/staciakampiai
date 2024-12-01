package com.example.demo.payments.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.payments.Domain.Entities.Tip;

 import java.util.UUID;

public interface ITipRepository extends JpaRepository<Tip, UUID> {
    Page<Tip> findByOrderId(UUID orderId, Pageable pageable);
}