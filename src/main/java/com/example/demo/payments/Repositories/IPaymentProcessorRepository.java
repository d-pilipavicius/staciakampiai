package com.example.demo.payments.Repositories;

import com.example.demo.payments.Domain.Entities.PaymentProcessor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface IPaymentProcessorRepository extends JpaRepository<PaymentProcessor, UUID> {}

