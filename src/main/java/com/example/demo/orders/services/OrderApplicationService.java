package com.example.demo.orders.services;

import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.helper.mapper.OrderMapper;
import com.example.demo.orders.domain.entities.Order;
import com.example.demo.orders.domain.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.demo.orders.API.DTOs.OrderDTOs.GetOrderDTO;
import com.example.demo.orders.API.DTOs.OrderDTOs.GetReceiptDTO;

@Service
public class OrderApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(OrderApplicationService.class);

    private final OrderService orderService;

    public OrderApplicationService(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Creates a Receipt object based on the specified order.
     *
     * @param orderDTO The order to create a receipt for.
     * @return The receipt for the specified order.
     */
    public GetReceiptDTO getOrderReceipt(GetOrderDTO orderDTO) {
        Order order;
        order = Mapper.mapToModel(orderDTO, OrderMapper.TO_MODEL);
        // TODO: mb take UUID as an argument and get the order from the repository?
        // TODO: Implement this method -> get stuff from other components and compose a ReceiptDTO object and return it
        return null;
    }
}
