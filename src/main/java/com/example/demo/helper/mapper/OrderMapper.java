package com.example.demo.helper.mapper;

import com.example.demo.helper.mapper.base.StaticMapper;
import com.example.demo.orders.domain.entities.Order;
import com.example.demo.orders.API.DTOs.OrderDTOs.ResponseOrderDTO;

public class OrderMapper {

    /**
     * Maps an OrderDTO to an Order entity.
     */
    public static final StaticMapper<ResponseOrderDTO, Order> TO_MODEL = dto -> {

        Order order = new Order();
        // ...
        return order;
    };

    /**
     * Maps an Order entity to an OrderDTO.
     */
    public static final StaticMapper<Order, ResponseOrderDTO> TO_DTO = entity -> {
        ResponseOrderDTO orderDTO = new ResponseOrderDTO();
        // ...
        return orderDTO;
    };

}
