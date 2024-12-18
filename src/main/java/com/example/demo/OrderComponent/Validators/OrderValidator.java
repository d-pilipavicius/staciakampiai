package com.example.demo.OrderComponent.Validators;

import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.NotFoundException;
import com.example.demo.OrderComponent.API.DTOs.OrderDTO;
import com.example.demo.OrderComponent.Domain.Entities.Enums.OrderStatus;
import com.example.demo.OrderComponent.Domain.Entities.Order;
import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.UnprocessableException;
import com.example.demo.OrderComponent.Repositories.IOrderItemRepository;
import com.example.demo.OrderComponent.Repositories.IOrderRepository;
import com.example.demo.payments.API.DTOs.OrderItemPaymentDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderValidator {

    private final IOrderRepository orderRepository;
    private final IOrderItemRepository orderItemRepository;
    public boolean isValidOrder(UUID orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new NotFoundException("Order not found");
        }
        return true;
    }

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

    public boolean isValidOrderItem(UUID orderId, UUID orderItemId) {
        if (!orderItemRepository.findByOrderId(orderId).stream().anyMatch(item -> item.getId().equals(orderItemId))) {
            throw new NotFoundException("Order item not found for the given order");
        }
        return true;
    }

    public void isOrderStatusClosed(Order order) {
        if (order.getStatus() == OrderStatus.CLOSED) {
            throw new UnprocessableException("Order is already closed and cannot be modified.");
        }
    }
}
