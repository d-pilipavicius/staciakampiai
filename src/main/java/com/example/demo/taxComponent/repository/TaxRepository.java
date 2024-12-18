package com.example.demo.taxComponent.repository;

import com.example.demo.discountComponent.domain.entities.Discount;
import com.example.demo.taxComponent.domain.entities.Tax;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;


public interface TaxRepository extends JpaRepository<Tax, UUID> {
    Page<Tax> findByBusinessId(UUID businessId, Pageable pageable);

    @Query("SELECT t.productIds FROM Tax t WHERE t.id = :taxId")
    List<UUID> findProductsByTaxId(@Param("taxId") UUID taxId);

}
