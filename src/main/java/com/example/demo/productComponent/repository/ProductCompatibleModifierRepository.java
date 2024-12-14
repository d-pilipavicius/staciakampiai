package com.example.demo.productComponent.repository;

import com.example.demo.productComponent.domain.entities.ProductCompatibleModifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductCompatibleModifierRepository extends JpaRepository<ProductCompatibleModifier, ProductCompatibleModifier> {
    boolean existsByProductIdAndModifierId(UUID productId, UUID modifierId);

    @Query("SELECT m.modifierId FROM ProductCompatibleModifier m WHERE m.productId = :productId")
    List<UUID> findModifierIdsByProductId(@Param("productId") UUID productId);

    void deleteByProductIdAndModifierId(UUID productId, UUID modifierId);

    void deleteByProductId(UUID productId);

    void deleteByModifierId(UUID modifierId);
}
