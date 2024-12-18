package com.example.demo.payments.Repositories;

import com.example.demo.payments.Domain.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface IPaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByOrderId(UUID orderId);
    List<Payment> findByTransactionId(String transactionId);
}