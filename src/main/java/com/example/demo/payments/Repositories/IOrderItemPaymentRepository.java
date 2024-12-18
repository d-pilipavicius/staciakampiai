package com.example.demo.payments.Repositories;

import com.example.demo.payments.Domain.Entities.OrderItemPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface IOrderItemPaymentRepository extends JpaRepository<OrderItemPayment, UUID> {
}
