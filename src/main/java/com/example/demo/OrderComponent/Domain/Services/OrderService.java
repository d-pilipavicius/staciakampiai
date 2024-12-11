package com.example.demo.OrderComponent.Domain.Services;

import com.example.demo.OrderComponent.API.DTOs.*;
import com.example.demo.OrderComponent.Domain.Entities.Enums.Currency;
import com.example.demo.OrderComponent.Domain.Entities.Order;
import com.example.demo.OrderComponent.Domain.Entities.OrderItem;
import com.example.demo.OrderComponent.Domain.Entities.Enums.OrderStatus;
import com.example.demo.OrderComponent.Domain.Entities.OrderItemModifier;
import com.example.demo.OrderComponent.Helpers.Mappers.OrderMapper;
import com.example.demo.OrderComponent.Helpers.OrderHelper;
import com.example.demo.OrderComponent.Repositories.IOrderItemModifierRepository;
import com.example.demo.OrderComponent.Repositories.IOrderRepository;
import com.example.demo.OrderComponent.Repositories.IOrderItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final IOrderRepository orderRepository;
    private final IOrderItemRepository orderItemRepository;
    private final IOrderItemModifierRepository orderItemModifierRepository;

    public OrderService(IOrderRepository orderRepository, IOrderItemRepository orderItemRepository, IOrderItemModifierRepository orderItemModifierRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderItemModifierRepository = orderItemModifierRepository;
    }

    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        Order order = new Order();
        order.setBusinessId(createOrderRequest.getBusinessId());
        order.setCreatedByEmployeeId(createOrderRequest.getCreatedByEmployeeId());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);
        order.setReservationId(createOrderRequest.getReservationId());

        Order savedOrder = orderRepository.save(order);
        BigDecimal originalPrice = BigDecimal.ZERO;
        Currency currency = Currency.EUR;

        List<OrderItem> savedItems = new ArrayList<>();
        for (OrderItemRequest itemRequest : createOrderRequest.getItems()) {
            OrderItem orderItem = OrderMapper.mapToOrderItem(itemRequest, savedOrder.getId());
            originalPrice = originalPrice.add(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            savedItems.add(savedOrderItem);

            if (itemRequest.getSelectedModifierIds() != null) {
                for (UUID modifierId : itemRequest.getSelectedModifierIds()) {
                    OrderItemModifier orderItemModifier = OrderMapper.mapToOrderItemModifier(savedOrderItem.getId(), modifierId);
                    orderItemModifierRepository.save(orderItemModifier);
                }
            }
        }

        originalPrice = OrderHelper.calculateOriginalPrice(savedItems, orderItemModifierRepository);

        List<OrderItemResponse> itemResponses = savedItems.stream().map(item -> {
            List<OrderItemModifier> modifiers = orderItemModifierRepository.findByOrderItemId(item.getId());
            return OrderMapper.mapToOrderItemResponse(item, modifiers);
        }).collect(Collectors.toList());

        return OrderMapper.mapToOrderResponse(savedOrder, itemResponses, originalPrice, currency);
    }

    public Page<OrderResponse> getOrders(int page, int pageSize) {
        Page<Order> orders = orderRepository.findAll(PageRequest.of(page - 1, pageSize));

        return orders.map(order -> {
            List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

            BigDecimal originalPrice = OrderHelper.calculateOriginalPrice(items, orderItemModifierRepository);
            Currency currency = OrderHelper.determineCurrency(items);

            List<OrderItemResponse> itemResponses = items.stream().map(item -> {
                List<OrderItemModifier> modifiers = orderItemModifierRepository.findByOrderItemId(item.getId());
                return OrderMapper.mapToOrderItemResponse(item, modifiers);
            }).collect(Collectors.toList());

            return OrderMapper.mapToOrderResponse(order, itemResponses, originalPrice, currency);
        });
    }

    public OrderResponse getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

        BigDecimal originalPrice = OrderHelper.calculateOriginalPrice(items, orderItemModifierRepository);
        Currency currency = OrderHelper.determineCurrency(items);

        List<OrderItemResponse> itemResponses = items.stream().map(item -> {
            List<OrderItemModifier> modifiers = orderItemModifierRepository.findByOrderItemId(item.getId());
            return OrderMapper.mapToOrderItemResponse(item, modifiers);
        }).collect(Collectors.toList());

        return OrderMapper.mapToOrderResponse(order, itemResponses, originalPrice, currency);
    }

    @Transactional
    public OrderResponse modifyOrder(UUID orderId, ModifyOrderRequest modifyOrderRequest) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.CLOSED) {
            throw new IllegalStateException("Order is already closed and cannot be modified.");
        }

        if (modifyOrderRequest.getReservationId() != null) {
            order.setReservationId(modifyOrderRequest.getReservationId());
        }

        if (modifyOrderRequest.getStatus() != null) {
            order.setStatus(modifyOrderRequest.getStatus());
        }

        List<OrderItem> existingItems = orderItemRepository.findByOrderId(order.getId());
        Map<UUID, OrderItem> existingItemsMap = existingItems.stream()
                .collect(Collectors.toMap(OrderItem::getId, item -> item));

        BigDecimal originalPrice = BigDecimal.ZERO;
        Currency currency = Currency.EUR;
        List<OrderItem> updatedItems = new ArrayList<>();

        if (modifyOrderRequest.getItems() != null) {
            for (OrderItemRequest itemRequest : modifyOrderRequest.getItems()) {
                OrderItem orderItem = existingItemsMap.get(itemRequest.getProductId());
                boolean isNewItem = orderItem == null;

                if (isNewItem) {
                    orderItem = new OrderItem();
                    orderItem.setOrderId(order.getId());
                }

                orderItem.setProductID(itemRequest.getProductId());
                orderItem.setTitle("Should get title from product table");
                orderItem.setQuantity(itemRequest.getQuantity());
                orderItem.setUnitPrice(BigDecimal.valueOf(2.5));  // Example, this should be fetched dynamically
                orderItem.setCurrency(Currency.EUR);

                originalPrice = originalPrice.add(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));

                OrderItem savedOrderItem = orderItemRepository.save(orderItem);
                updatedItems.add(savedOrderItem);

                List<UUID> newModifierIds = itemRequest.getSelectedModifierIds() != null ? itemRequest.getSelectedModifierIds() : Collections.emptyList();
                List<OrderItemModifier> existingModifiers = orderItemModifierRepository.findByOrderItemId(savedOrderItem.getId());
                Map<UUID, OrderItemModifier> existingModifiersMap = existingModifiers.stream()
                        .collect(Collectors.toMap(OrderItemModifier::getProductModifierId, modifier -> modifier));

                for (UUID modifierId : newModifierIds) {
                    OrderItemModifier modifier = existingModifiersMap.get(modifierId);
                    if (modifier == null) {
                        modifier = new OrderItemModifier();
                        modifier.setOrderItemId(savedOrderItem.getId());
                        modifier.setProductModifierId(modifierId);
                        modifier.setTitle("Hardcoded Modifier Title");
                        modifier.setPrice(BigDecimal.valueOf(1.0));  // Example, this should be fetched dynamically
                        modifier.setCurrency(Currency.EUR);
                    }

                    orderItemModifierRepository.save(modifier);
                }

                existingModifiersMap.keySet().stream()
                        .filter(modifierId -> !newModifierIds.contains(modifierId))
                        .forEach(modifierId -> orderItemModifierRepository.delete(existingModifiersMap.get(modifierId)));
            }
        }

        Set<UUID> newItemIds = modifyOrderRequest.getItems().stream()
                .map(OrderItemRequest::getProductId)
                .collect(Collectors.toSet());

        existingItemsMap.keySet().stream()
                .filter(itemId -> !newItemIds.contains(itemId))
                .forEach(itemId -> {
                    orderItemModifierRepository.deleteByOrderItemId(itemId);
                    orderItemRepository.deleteById(itemId);
                });

        Order savedOrder = orderRepository.save(order);

        originalPrice = OrderHelper.calculateOriginalPrice(updatedItems, orderItemModifierRepository);

        List<OrderItemResponse> itemResponses = updatedItems.stream().map(item -> {
            List<OrderItemModifier> modifiers = orderItemModifierRepository.findByOrderItemId(item.getId());
            return OrderMapper.mapToOrderItemResponse(item, modifiers);
        }).collect(Collectors.toList());

        return OrderMapper.mapToOrderResponse(savedOrder, itemResponses, originalPrice, currency);
    }
}