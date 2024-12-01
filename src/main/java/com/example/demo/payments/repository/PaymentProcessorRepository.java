package com.example.demo.payments.repository;

import com.example.demo.payments.domain.entities.PaymentProcessor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface PaymentProcessorRepository extends JpaRepository<PaymentProcessor, UUID> {}

