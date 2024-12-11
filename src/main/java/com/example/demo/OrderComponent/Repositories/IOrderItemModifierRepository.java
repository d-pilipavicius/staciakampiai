package com.example.demo.OrderComponent.Repositories;

import com.example.demo.OrderComponent.Domain.Entities.OrderItemModifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IOrderItemModifierRepository extends JpaRepository<OrderItemModifier, UUID> {

    void deleteByOrderItemId(UUID itemId);

    List<OrderItemModifier> findByOrderItemIdIn(List<UUID> collect);

    List<OrderItemModifier> findByOrderItemId(UUID id);
}
