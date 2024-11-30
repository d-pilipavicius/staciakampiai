package com.example.demo.productComponent.repository;

import com.example.demo.productComponent.domain.entities.ProductModifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductModifierRepository extends JpaRepository<ProductModifier, UUID> {

    long countByIdIn(List<UUID> modifierIds);

    List<ProductModifier> findAllByBusinessId(PageRequest pageRequest, UUID businessId);
}
