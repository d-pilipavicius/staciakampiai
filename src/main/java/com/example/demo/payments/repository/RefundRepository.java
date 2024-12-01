package com.example.demo.payments.repository;

import com.example.demo.payments.domain.entities.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RefundRepository extends JpaRepository<Refund, UUID> {
}
