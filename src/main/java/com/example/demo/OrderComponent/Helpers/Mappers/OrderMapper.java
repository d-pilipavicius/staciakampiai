package com.example.demo.OrderComponent.Helpers.Mappers;

import com.example.demo.helper.enums.Currency;
import com.example.demo.OrderComponent.API.DTOs.OrderItemModifierDTO;
import com.example.demo.OrderComponent.API.DTOs.OrderItemDTO;
import com.example.demo.OrderComponent.API.DTOs.OrderDTO;
import com.example.demo.OrderComponent.Domain.Entities.Order;
import com.example.demo.OrderComponent.Domain.Entities.OrderItem;
import com.example.demo.OrderComponent.Domain.Entities.OrderItemModifier;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderMapper {

    public static List<OrderItemModifierDTO> mapToOrderItemModifierResponse(List<ProductModifierDTO> modifiers) {
        return modifiers.stream()
                .map(modifier -> OrderItemModifierDTO.builder()
                        .id(modifier.getId())
                        .title(modifier.getTitle())
                        .price(modifier.getPrice().getAmount())
                        .currency(modifier.getPrice().getCurrency())
                        .build())
                .collect(Collectors.toList());
    }

    public static OrderItemDTO mapToOrderItemResponse(OrderItem item, List<OrderItemModifier> modifiers) {
        List<OrderItemModifierDTO> modifierResponses = modifiers.stream()
                .map(modifier -> OrderItemModifierDTO.builder()
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

        return OrderItemDTO.builder()
                .id(item.getId())
                .productId(item.getProductID())
                .title(item.getTitle())
                .quantity(item.getQuantity())
                .unitPrice(OrderItemDTO.UnitPrice.builder()
                        .base(basePrice)
                        .withModifiers(withModifiersPrice)
                        .build())
                .currency(item.getCurrency())
                .originalPrice(originalPrice)
                .modifiers(modifierResponses)
                .build();
    }

    public static OrderDTO mapToOrderResponse(Order order, List<OrderItemDTO> itemResponses, BigDecimal originalPrice, Currency currency) {
        return OrderDTO.builder()
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

    public static OrderItem mapToOrderItem(OrderItemDTO itemRequest, UUID orderId) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setProductID(itemRequest.getProductId());
        orderItem.setTitle(itemRequest.getTitle());
        orderItem.setQuantity(itemRequest.getQuantity());
        orderItem.setUnitPrice(itemRequest.getUnitPrice().getBase());
        orderItem.setCurrency(itemRequest.getCurrency());
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