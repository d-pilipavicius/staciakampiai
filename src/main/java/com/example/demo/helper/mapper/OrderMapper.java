package com.example.demo.helper.mapper;

import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.orders.domain.entities.Order;
import com.example.demo.orders.domain.services.OrderService;
import com.example.demo.orders.services.OrderApplicationService;

public class OrderMapper {

    /**
     * Maps an OrderDTO to an Order entity.
     */
    public static final StaticMapper<OrderService.OrderDTO, Order> TO_MODEL = dto -> {
        Order order = new Order();
        // ...
        return order;
    };

    /**
     * Maps an Order entity to an OrderDTO.
     */
    public static final StaticMapper<Order, OrderService.OrderDTO> TO_DTO = entity -> {
        OrderService.OrderDTO orderDTO = new OrderService.OrderDTO();
        // ...
        return orderDTO;
    };
}
