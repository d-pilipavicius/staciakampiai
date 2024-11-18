package com.example.demo.orders.repository;

import com.example.demo.orders.domain.entities.Order;
import com.example.demo.orders.domain.entities.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startTimestamp AND :endTimestamp")
    List<Order> findOrdersInTimestampRange(@Param("startTimestamp") Timestamp startTimestamp, @Param("endTimestamp") Timestamp endTimestamp);
}
