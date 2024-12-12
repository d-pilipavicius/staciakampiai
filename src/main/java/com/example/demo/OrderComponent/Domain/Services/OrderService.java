package com.example.demo.OrderComponent.Domain.Services;

import com.example.demo.helper.enums.Currency;
import com.example.demo.OrderComponent.API.DTOs.*;
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

    public OrderDTO createOrder(OrderDTO createOrderDTO) {
        Order order = new Order();
        order.setBusinessId(createOrderDTO.getBusinessId());
        order.setCreatedByEmployeeId(createOrderDTO.getEmployeeId());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);
        order.setReservationId(createOrderDTO.getReservationId());

        Order savedOrder = orderRepository.save(order);
        BigDecimal originalPrice = BigDecimal.ZERO;
        Currency currency = Currency.EUR;

        List<OrderItem> savedItems = new ArrayList<>();
        for (OrderItemDTO itemRequest : createOrderDTO.getItems()) {
            OrderItem orderItem = OrderMapper.mapToOrderItem(itemRequest, savedOrder.getId());
            originalPrice = originalPrice.add(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            savedItems.add(savedOrderItem);

            if (itemRequest.getModifiers() != null) {
                for (OrderItemModifierDTO modifierDTO : itemRequest.getModifiers()) {
                    OrderItemModifier orderItemModifier = new OrderItemModifier();
                    orderItemModifier.setOrderItemId(savedOrderItem.getId());
                    orderItemModifier.setProductModifierId(modifierDTO.getId());
                    orderItemModifier.setTitle(modifierDTO.getTitle());
                    orderItemModifier.setPrice(modifierDTO.getPrice());
                    orderItemModifier.setCurrency(modifierDTO.getCurrency());
                    orderItemModifierRepository.save(orderItemModifier);
                }
            }
        }

        originalPrice = OrderHelper.calculateOriginalPrice(savedItems, orderItemModifierRepository);

        List<OrderItemDTO> itemResponses = savedItems.stream().map(item -> {
            List<OrderItemModifier> modifiers = orderItemModifierRepository.findByOrderItemId(item.getId());
            return OrderMapper.mapToOrderItemResponse(item, modifiers);
        }).collect(Collectors.toList());

        return OrderMapper.mapToOrderResponse(savedOrder, itemResponses, originalPrice, currency);
    }

    public Page<OrderDTO> getOrders(int page, int pageSize) {
        Page<Order> orders = orderRepository.findAll(PageRequest.of(page - 1, pageSize));

        return orders.map(order -> {
            List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

            BigDecimal originalPrice = OrderHelper.calculateOriginalPrice(items, orderItemModifierRepository);
            Currency currency = OrderHelper.determineCurrency(items);

            List<OrderItemDTO> itemResponses = items.stream().map(item -> {
                List<OrderItemModifier> modifiers = orderItemModifierRepository.findByOrderItemId(item.getId());
                return OrderMapper.mapToOrderItemResponse(item, modifiers);
            }).collect(Collectors.toList());

            return OrderMapper.mapToOrderResponse(order, itemResponses, originalPrice, currency);
        });
    }

    public OrderDTO getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

        BigDecimal originalPrice = OrderHelper.calculateOriginalPrice(items, orderItemModifierRepository);
        Currency currency = OrderHelper.determineCurrency(items);

        List<OrderItemDTO> itemResponses = items.stream().map(item -> {
            List<OrderItemModifier> modifiers = orderItemModifierRepository.findByOrderItemId(item.getId());
            return OrderMapper.mapToOrderItemResponse(item, modifiers);
        }).collect(Collectors.toList());

        return OrderMapper.mapToOrderResponse(order, itemResponses, originalPrice, currency);
    }

    @Transactional
    public OrderDTO modifyOrder(UUID orderId, ModifyOrderDTO modifyOrderRequest) {
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
            for (OrderItemDTO itemRequest : modifyOrderRequest.getItems()) {
                OrderItem orderItem = existingItemsMap.get(itemRequest.getProductId());
                boolean isNewItem = orderItem == null;

                if (isNewItem) {
                    orderItem = new OrderItem();
                    orderItem.setOrderId(order.getId());
                }

                orderItem.setProductID(itemRequest.getProductId());
                orderItem.setTitle(itemRequest.getTitle());
                orderItem.setQuantity(itemRequest.getQuantity());
                orderItem.setUnitPrice(itemRequest.getUnitPrice().getBase());
                orderItem.setCurrency(itemRequest.getCurrency());

                originalPrice = originalPrice.add(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));

                OrderItem savedOrderItem = orderItemRepository.save(orderItem);
                updatedItems.add(savedOrderItem);

                if (itemRequest.getModifiers() != null) {
                    for (OrderItemModifierDTO modifierDTO : itemRequest.getModifiers()) {
                        OrderItemModifier orderItemModifier = new OrderItemModifier();
                        orderItemModifier.setOrderItemId(savedOrderItem.getId());
                        orderItemModifier.setProductModifierId(modifierDTO.getId());
                        orderItemModifier.setTitle(modifierDTO.getTitle());
                        orderItemModifier.setPrice(modifierDTO.getPrice());
                        orderItemModifier.setCurrency(modifierDTO.getCurrency());
                        orderItemModifierRepository.save(orderItemModifier);
                    }
                }
            }
        }

        Set<UUID> newItemIds = modifyOrderRequest.getItems().stream()
                .map(OrderItemDTO::getProductId)
                .collect(Collectors.toSet());

        existingItemsMap.keySet().stream()
                .filter(itemId -> !newItemIds.contains(itemId))
                .forEach(itemId -> {
                    orderItemModifierRepository.deleteByOrderItemId(itemId);
                    orderItemRepository.deleteById(itemId);
                });

        Order savedOrder = orderRepository.save(order);

        originalPrice = OrderHelper.calculateOriginalPrice(updatedItems, orderItemModifierRepository);

        List<OrderItemDTO> itemResponses = updatedItems.stream().map(item -> {
            List<OrderItemModifier> modifiers = orderItemModifierRepository.findByOrderItemId(item.getId());
            return OrderMapper.mapToOrderItemResponse(item, modifiers);
        }).collect(Collectors.toList());

        return OrderMapper.mapToOrderResponse(savedOrder, itemResponses, originalPrice, currency);
    }

    public OrderDTO getOrderReceipt(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

        BigDecimal originalPrice = OrderHelper.calculateOriginalPrice(items, orderItemModifierRepository);
        Currency currency = OrderHelper.determineCurrency(items);

        List<OrderItemDTO> itemResponses = items.stream().map(item -> {
            List<OrderItemModifier> modifiers = orderItemModifierRepository.findByOrderItemId(item.getId());
            return OrderMapper.mapToOrderItemResponse(item, modifiers);
        }).collect(Collectors.toList());

        return OrderMapper.mapToOrderResponse(order, itemResponses, originalPrice, currency);
    }
}