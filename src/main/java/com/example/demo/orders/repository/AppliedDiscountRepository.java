package com.example.demo.orders.repository;

import com.example.demo.orders.domain.entities.AppliedDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppliedDiscountRepository extends JpaRepository<AppliedDiscount, UUID> {

}
