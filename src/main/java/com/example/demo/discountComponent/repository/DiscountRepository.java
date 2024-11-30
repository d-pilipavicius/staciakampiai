package com.example.demo.discountComponent.repository;

import com.example.demo.discountComponent.domain.entities.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, UUID> {
    Page<Discount> findByBusinessId(UUID businessId, Pageable pageable);
}
