package com.example.demo.OrderComponent.Domain.Services;

import com.example.demo.OrderComponent.API.DTOs.ModifyOrderDTO;
import com.example.demo.OrderComponent.API.DTOs.OrderItemDTO;
import com.example.demo.OrderComponent.API.DTOs.OrderItemModifierDTO;
import com.example.demo.OrderComponent.Domain.Entities.Order;
import com.example.demo.OrderComponent.Domain.Entities.OrderItem;
import com.example.demo.OrderComponent.Domain.Entities.OrderItemModifier;
import com.example.demo.OrderComponent.Helpers.Mappers.OrderMapper;
import com.example.demo.OrderComponent.Helpers.OrderHelper;
import com.example.demo.OrderComponent.Repositories.IOrderItemModifierRepository;
import com.example.demo.OrderComponent.Repositories.IOrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderItemService {
    private final IOrderItemRepository orderItemRepository;
    private final IOrderItemModifierRepository orderItemModifierRepository;

    public List<OrderItem> saveOrderItems(List<OrderItemDTO> items, UUID orderId) {
        List<OrderItem> savedItems = new ArrayList<>();
        for (OrderItemDTO item : items) {
            OrderItem orderItem = OrderMapper.mapToOrderItem(item, orderId);
            savedItems.add(orderItemRepository.save(orderItem));
            saveModifiers(item.getModifiers(), orderItem.getId());
        }
        return savedItems;
    }

    public List<OrderItem> modifyOrderItems(Order order, ModifyOrderDTO modifyOrderRequest) {
        List<OrderItem> existingItems = getItemsByOrderId(order.getId());
        Map<UUID, OrderItem> existingItemsMap = existingItems.stream()
                .collect(Collectors.toMap(OrderItem::getId, item -> item));

        List<OrderItem> updatedItems = processItems(modifyOrderRequest.getItems(), existingItemsMap, order.getId());
        deleteRemovedItems(existingItemsMap, modifyOrderRequest.getItems());
        return updatedItems;
    }

    public List<OrderItem> getItemsByOrderId(UUID orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    private List<OrderItem> processItems(List<OrderItemDTO> items, Map<UUID, OrderItem> existingItemsMap, UUID orderId) {
        List<OrderItem> updatedItems = new ArrayList<>();
        if (items != null) {
            for (OrderItemDTO itemRequest : items) {
                OrderItem orderItem = existingItemsMap.get(itemRequest.getProductId());
                boolean isNewItem = orderItem == null;

                if (isNewItem) {
                    orderItem = OrderMapper.mapToOrderItem(itemRequest, orderId);
                } else {
                    OrderMapper.mapToExistingOrderItem(orderItem, itemRequest);
                }

                updatedItems.add(orderItemRepository.save(orderItem));
                saveModifiers(itemRequest.getModifiers(), orderItem.getId());
            }
        }
        return updatedItems;
    }

    private void deleteRemovedItems(Map<UUID, OrderItem> existingItemsMap, List<OrderItemDTO> items) {
        Set<UUID> newItemIds = items.stream()
                .map(OrderItemDTO::getProductId)
                .collect(Collectors.toSet());

        existingItemsMap.keySet().stream()
                .filter(itemId -> !newItemIds.contains(itemId))
                .forEach(itemId -> {
                    orderItemModifierRepository.deleteByOrderItemId(itemId);
                    orderItemRepository.deleteById(itemId);
                });
    }

    public BigDecimal calculateOriginalPrice(List<OrderItem> items) {
        return items.stream()
                .map(item -> OrderHelper.calculateItemPrice(item, orderItemModifierRepository))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<OrderItemDTO> mapToOrderItemResponses(List<OrderItem> items) {
        return items.stream().map(item -> {
            List<OrderItemModifier> modifiers = orderItemModifierRepository.findByOrderItemId(item.getId());
            return OrderMapper.mapToOrderItemResponse(item, modifiers);
        }).collect(Collectors.toList());
    }

    private void saveModifiers(List<OrderItemModifierDTO> modifiers, UUID orderItemId) {
        if (modifiers != null) {
            modifiers.forEach(modifierDTO -> {
                OrderItemModifier modifier = OrderMapper.mapToOrderItemModifier(modifierDTO, orderItemId);
                orderItemModifierRepository.save(modifier);
            });
        }
    }
}
