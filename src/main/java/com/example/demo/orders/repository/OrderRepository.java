package com.example.demo.orders.repository;

import com.example.demo.orders.domain.entities.Order;
import com.example.demo.orders.domain.entities.enums.OrderStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {

    List<Order> findOrderByStatus(OrderStatus status);
}
