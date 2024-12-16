package com.example.demo.OrderComponent.Repositories;

import com.example.demo.OrderComponent.Domain.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IOrderRepository extends JpaRepository<Order, UUID> {
}