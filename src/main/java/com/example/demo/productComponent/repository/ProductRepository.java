package com.example.demo.productComponent.repository;

import com.example.demo.productComponent.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    long countByIdIn(List<UUID> productIds);

    List<Product> findAllByBusinessId(UUID businessId);
    Page<Product> findAllByBusinessId(UUID businessId, Pageable pageable);

    List<Product> findByIdIn(List<UUID> productIds);
}
