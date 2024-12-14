package com.example.demo.OrderComponent.Validators;

import com.example.demo.OrderComponent.API.DTOs.OrderDTO;
import com.example.demo.OrderComponent.Domain.Entities.Enums.OrderStatus;
import com.example.demo.OrderComponent.Domain.Entities.Order;
import com.example.demo.helper.ErrorHandling.CustomExceptions.UnprocessableException;
import org.springframework.stereotype.Service;

@Service
public class OrderValidator {

    public void validateCreateOrder(OrderDTO orderDTO) {
        if (orderDTO.getItems() == null || orderDTO.getItems().isEmpty()) {
            throw new UnprocessableException("Order must contain at least one item.");
        }
    }

    public void validateModifiableOrder(Order order) {
        if (order.getStatus() == OrderStatus.CLOSED) {
            throw new UnprocessableException("Order is already closed and cannot be modified.");
        }
    }
}
