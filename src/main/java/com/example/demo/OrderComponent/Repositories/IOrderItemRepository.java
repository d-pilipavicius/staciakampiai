package com.example.demo.OrderComponent.Repositories;

import com.example.demo.OrderComponent.Domain.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IOrderItemRepository extends JpaRepository<OrderItem, UUID>{
    List<OrderItem> findByOrderId(UUID orderId);
}
