package com.example.demo.helper.mapper;

import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.orders.domain.entities.Order;
import com.example.demo.orders.API.DTOs.OrderDTOs.GetOrderDTO;

public class OrderMapper {

    /**
     * Maps an OrderDTO to an Order entity.
     */
    public static final StaticMapper<GetOrderDTO, Order> TO_MODEL = dto -> {

        Order order = new Order();
        // ...
        return order;
    };

    /**
     * Maps an Order entity to an OrderDTO.
     */
    public static final StaticMapper<Order, GetOrderDTO> TO_DTO = entity -> {
        GetOrderDTO orderDTO = new GetOrderDTO();
        // ...
        return orderDTO;
    };

}
