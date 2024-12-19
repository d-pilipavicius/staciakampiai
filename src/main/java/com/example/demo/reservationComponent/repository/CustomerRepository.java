package com.example.demo.reservationComponent.repository;

import com.example.demo.reservationComponent.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
