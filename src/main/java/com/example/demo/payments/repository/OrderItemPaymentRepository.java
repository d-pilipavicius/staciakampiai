package com.example.demo.payments.repository;

import com.example.demo.payments.domain.entities.OrderItemPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface OrderItemPaymentRepository extends JpaRepository<OrderItemPayment, UUID> {
}
