package com.example.demo.payments.Repositories;

import com.example.demo.payments.Domain.Entities.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IRefundRepository extends JpaRepository<Refund, UUID> {
}
