package com.example.demo.OrderComponent.Helpers.Mappers;

import com.example.demo.OrderComponent.API.DTOs.AppliedServiceChargeDTO;
import com.example.demo.OrderComponent.Domain.Entities.AppliedServiceCharge;
import com.example.demo.OrderComponent.Domain.Entities.Enums.OrderStatus;
import com.example.demo.CommonHelper.enums.Currency;
import com.example.demo.OrderComponent.API.DTOs.OrderItemModifierDTO;
import com.example.demo.OrderComponent.API.DTOs.OrderItemDTO;
import com.example.demo.OrderComponent.API.DTOs.OrderDTO;
import com.example.demo.OrderComponent.Domain.Entities.Order;
import com.example.demo.OrderComponent.Domain.Entities.OrderItem;
import com.example.demo.OrderComponent.Domain.Entities.OrderItemModifier;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
import com.example.demo.serviceChargeComponent.api.dtos.ServiceChargeHelperDTOs.ServiceChargeDTO;
import com.example.demo.OrderComponent.Helpers.OrderHelper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
                        .productModifierId(modifier.getProductModifierId())
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

    public static OrderDTO mapToOrderResponse(Order order, List<OrderItemDTO> itemResponses, BigDecimal originalPrice, Currency currency, List<AppliedServiceCharge> appliedServiceCharges) {
        List<AppliedServiceChargeDTO> serviceChargeResponses = appliedServiceCharges.stream()
                .map(serviceCharge -> {
                    BigDecimal amount = OrderHelper.calculateServiceChargeAmount(serviceCharge, originalPrice);

                    return toAppliedServiceChargeDTO(serviceCharge, amount);
                })
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .id(order.getId())
                .businessId(order.getBusinessId())
                .employeeId(order.getCreatedByEmployeeId())
                .createdAt(order.getCreatedAt())
                .status(order.getStatus())
                .reservationId(order.getReservationId())
                .items(itemResponses)
                .originalPrice(originalPrice)
                .finalPrice(originalPrice.add(serviceChargeResponses.stream()
                        .map(AppliedServiceChargeDTO::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)))
                .currency(currency)
                .serviceCharges(serviceChargeResponses)
                .build();
    }

    public static AppliedServiceChargeDTO toAppliedServiceChargeDTO(AppliedServiceCharge serviceCharge, BigDecimal amount) {
        return AppliedServiceChargeDTO.builder()
                .id(serviceCharge.getId())
                .chargedByEmployeeId(serviceCharge.getEmployeeId())
                .title(serviceCharge.getTitle())
                .valueType(serviceCharge.getValueType())
                .value(serviceCharge.getValue())
                .amount(amount)
                .currency(serviceCharge.getCurrency())
                .build();
    }

    public static OrderItem mapToOrderItem(OrderItemDTO itemRequest, UUID orderId) {
        return OrderItem.builder()
                .orderId(orderId)
                .productID(itemRequest.getProductId())
                .title(itemRequest.getTitle())
                .quantity(itemRequest.getQuantity())
                .unitPrice(itemRequest.getUnitPrice().getBase())
                .currency(itemRequest.getCurrency())
                .build();
    }

    public static OrderDTO.OrderDTOBuilder mapToOrderReceipt(OrderDTO orderDTO) {
        return OrderDTO.builder()
                .id(orderDTO.getId())
                .items(orderDTO.getItems())
                .originalPrice(orderDTO.getOriginalPrice())
                .currency(orderDTO.getCurrency());
    }

    public static OrderItemModifier mapToOrderItemModifier(OrderItemModifierDTO modifierDTO, UUID orderItemId) {
        return OrderItemModifier.builder()
                .orderItemId(orderItemId)
                .productModifierId(modifierDTO.getId())
                .title(modifierDTO.getTitle())
                .price(modifierDTO.getPrice())
                .currency(modifierDTO.getCurrency())
                .build();
    }

    public static Order mapToOrder(OrderDTO createOrderDTO) {
        return Order.builder()
                .businessId(createOrderDTO.getBusinessId())
                .createdByEmployeeId(createOrderDTO.getEmployeeId())
                .createdAt(LocalDateTime.now())
                .status(OrderStatus.NEW)
                .reservationId(createOrderDTO.getReservationId())
                .build();
    }

    public static void mapToExistingOrderItem(OrderItem existingItem, OrderItemDTO itemRequest) {
        existingItem.setProductID(itemRequest.getProductId());
        existingItem.setTitle(itemRequest.getTitle());
        existingItem.setQuantity(itemRequest.getQuantity());
        existingItem.setUnitPrice(itemRequest.getUnitPrice().getBase());
        existingItem.setCurrency(itemRequest.getCurrency());
    }
    public static AppliedServiceChargeDTO mapToAppliedServiceChargeDTO(ServiceChargeDTO serviceChargeDTO) {
        return AppliedServiceChargeDTO.builder()
                .id(serviceChargeDTO.getId())
                .title(serviceChargeDTO.getTitle())
                .value(serviceChargeDTO.getServiceChargeValue())
                .valueType(serviceChargeDTO.getValueType())
                .currency(serviceChargeDTO.getCurrency().orElse(null))
                .build();
    }

    public static AppliedServiceCharge mapToAppliedServiceCharge(AppliedServiceChargeDTO serviceChargeDTO, Order savedOrder) {
        return AppliedServiceCharge.builder()
                .title(serviceChargeDTO.getTitle())
                .value(serviceChargeDTO.getValue())
                .valueType(serviceChargeDTO.getValueType())
                .currency(serviceChargeDTO.getCurrency())
                .order(savedOrder)
                .employeeId(savedOrder.getCreatedByEmployeeId())
                .reservation(savedOrder.getReservationId())
                .build();
    }

}