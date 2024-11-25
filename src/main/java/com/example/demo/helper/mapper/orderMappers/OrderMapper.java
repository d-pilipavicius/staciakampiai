package com.example.demo.helper.mapper.orderMappers;

import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.orders.API.DTOs.OrderDTOs.OrderHelperDTOs.OrderDTO;
import com.example.demo.orders.API.DTOs.OrderDTOs.PostOrderDTO;
import com.example.demo.orders.domain.entities.Order;
import com.example.demo.orders.domain.entities.enums.OrderStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class OrderMapper {

    /**
     * Maps an OrderDTO to an Order entity.
     */
    public static final StaticMapper<PostOrderDTO, Order> TO_MODEL = dto -> {
        Order order = new Order();
        dto.getReservationId().ifPresent(order::setReservationId);
        order.setBusinessId(dto.getBusinessId());
        order.setStatus(OrderStatus.NEW);
        order.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        // todo: EmployeeId set in service
        return order;
    };

    /**
     * Maps an Order entity to an OrderDTO.
     */
    public static final StaticMapper<Order, OrderDTO> TO_DTO = entity -> {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(entity.getId());
        orderDTO.setEmployeeId(entity.getEmployeeId());
        orderDTO.setReservationId(Optional.ofNullable(entity.getReservationId()));
        orderDTO.setStatus(entity.getStatus());
        orderDTO.setCreatedAt(entity.getCreatedAt());
        // TODO: clodedAt -> set is service
        // TODO: calculate and set all the prices in service
        return orderDTO;
    };

}
