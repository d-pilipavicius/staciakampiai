package com.example.demo.OrderComponent.Helpers.Mappers;

import com.example.demo.OrderComponent.API.DTOs.OrderItemModifierResponse;
import com.example.demo.OrderComponent.API.DTOs.OrderItemRequest;
import com.example.demo.OrderComponent.API.DTOs.OrderItemResponse;
import com.example.demo.OrderComponent.API.DTOs.OrderResponse;
import com.example.demo.OrderComponent.Domain.Entities.Enums.Currency;
import com.example.demo.OrderComponent.Domain.Entities.Order;
import com.example.demo.OrderComponent.Domain.Entities.OrderItem;
import com.example.demo.OrderComponent.Domain.Entities.OrderItemModifier;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderItemResponse mapToOrderItemResponse(OrderItem item, List<OrderItemModifier> modifiers) {
        List<OrderItemModifierResponse> modifierResponses = modifiers.stream()
                .map(modifier -> OrderItemModifierResponse.builder()
                        .id(modifier.getId())
                        .title(modifier.getTitle())
                        .price(modifier.getPrice())
                        .currency(modifier.getCurrency())
                        .build())
                .collect(Collectors.toList());

        BigDecimal basePrice = item.getUnitPrice();
        BigDecimal withModifiersPrice = basePrice.add(modifiers.stream()
                .map(OrderItemModifier::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        BigDecimal originalPrice = withModifiersPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

        return OrderItemResponse.builder()
                .id(item.getId())
                .productId(item.getProductID())
                .title(item.getTitle())
                .quantity(item.getQuantity())
                .unitPrice(OrderItemResponse.UnitPrice.builder()
                        .base(basePrice)
                        .withModifiers(withModifiersPrice)
                        .build())
                .currency(item.getCurrency())
                .originalPrice(originalPrice)
                .modifiers(modifierResponses)
                .build();
    }

    public static OrderResponse mapToOrderResponse(Order order, List<OrderItemResponse> itemResponses, BigDecimal originalPrice, Currency currency) {
        return OrderResponse.builder()
                .id(order.getId())
                .businessId(order.getBusinessId())
                .employeeId(order.getCreatedByEmployeeId())
                .createdAt(order.getCreatedAt())
                .status(order.getStatus())
                .reservationId(order.getReservationId())
                .items(itemResponses)
                .originalPrice(originalPrice)
                .currency(currency)
                .build();
    }

    public static OrderItem mapToOrderItem(OrderItemRequest itemRequest, UUID orderId) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setProductID(itemRequest.getProductId());
        orderItem.setTitle("Should get title from product table");
        orderItem.setQuantity(itemRequest.getQuantity());
        orderItem.setUnitPrice(BigDecimal.valueOf(2.5));
        orderItem.setCurrency(Currency.EUR);
        return orderItem;
    }

    public static OrderItemModifier mapToOrderItemModifier(UUID orderItemId, UUID modifierId) {
        OrderItemModifier orderItemModifier = new OrderItemModifier();
        orderItemModifier.setOrderItemId(orderItemId);
        orderItemModifier.setProductModifierId(modifierId);
        orderItemModifier.setTitle("Hardcoded Modifier Title");
        orderItemModifier.setPrice(BigDecimal.valueOf(1.0));
        orderItemModifier.setCurrency(Currency.EUR);
        return orderItemModifier;
    }
}