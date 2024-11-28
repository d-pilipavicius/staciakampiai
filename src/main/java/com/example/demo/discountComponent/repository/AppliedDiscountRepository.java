package com.example.demo.discountComponent.repository;

import com.example.demo.discountComponent.domain.entities.AppliedDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppliedDiscountRepository extends JpaRepository<AppliedDiscount, UUID> {

}
