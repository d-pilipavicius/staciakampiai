package com.example.demo.OrderComponent.Domain.Services;

import com.example.demo.OrderComponent.Domain.Entities.AppliedServiceCharge;
import com.example.demo.OrderComponent.Repositories.IAppliedServiceChargeRepository;
import com.example.demo.OrderComponent.Validators.OrderValidator;
import com.example.demo.helper.enums.Currency;
import com.example.demo.OrderComponent.API.DTOs.*;
import com.example.demo.OrderComponent.Domain.Entities.Order;
import com.example.demo.OrderComponent.Domain.Entities.OrderItem;
import com.example.demo.OrderComponent.Domain.Entities.OrderItemModifier;
import com.example.demo.OrderComponent.Helpers.Mappers.OrderMapper;
import com.example.demo.OrderComponent.Helpers.OrderHelper;
import com.example.demo.OrderComponent.Repositories.IOrderItemModifierRepository;
import com.example.demo.OrderComponent.Repositories.IOrderRepository;
import com.example.demo.OrderComponent.Repositories.IOrderItemRepository;
import com.example.demo.helper.ErrorHandling.CustomExceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final IOrderRepository orderRepository;
    private final IOrderItemRepository orderItemRepository;
    private final IOrderItemModifierRepository orderItemModifierRepository;
    private final IAppliedServiceChargeRepository appliedServiceChargeRepository;
    private final OrderItemService orderItemService;
    private final OrderChargeService orderChargeService;
    private final OrderValidator orderValidator;

    @Transactional
    public OrderDTO createOrder(OrderDTO createOrderDTO) {
        orderValidator.validateCreateOrder(createOrderDTO);

        Order order = OrderMapper.mapToOrder(createOrderDTO);
        Order savedOrder = orderRepository.save(order);

        List<OrderItem> savedItems = orderItemService.saveOrderItems(createOrderDTO.getItems(), savedOrder.getId());
        BigDecimal originalPrice = orderItemService.calculateOriginalPrice(savedItems);

        List<OrderItemDTO> itemResponses = orderItemService.mapToOrderItemResponses(savedItems);
        List<AppliedServiceCharge> appliedServiceCharges =
                orderChargeService.saveServiceCharges(createOrderDTO.getServiceCharges(), savedOrder);

        return OrderMapper.mapToOrderResponse(
                savedOrder, itemResponses, originalPrice, createOrderDTO.getCurrency(), appliedServiceCharges);
    }

    public Page<OrderDTO> getOrders(int page, int pageSize) {
        Page<Order> orders = orderRepository.findAll(PageRequest.of(page - 1, pageSize));
        return orders.map(order -> {
            List<OrderItem> items = orderItemService.getItemsByOrderId(order.getId());
            BigDecimal originalPrice = orderItemService.calculateOriginalPrice(items);
            List<OrderItemDTO> itemResponses = orderItemService.mapToOrderItemResponses(items);
            List<AppliedServiceCharge> charges = orderChargeService.findAppliedServiceChargesByOrderId(order.getId());
            return OrderMapper.mapToOrderResponse(order, itemResponses, originalPrice, OrderHelper.determineCurrency(items), charges);
        });
    }

    public OrderDTO getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        List<OrderItem> items = orderItemService.getItemsByOrderId(order.getId());
        BigDecimal originalPrice = orderItemService.calculateOriginalPrice(items);
        List<OrderItemDTO> itemResponses = orderItemService.mapToOrderItemResponses(items);

        return OrderMapper.mapToOrderResponse(
                order, itemResponses, originalPrice, OrderHelper.determineCurrency(items),
                orderChargeService.findAppliedServiceChargesByOrderId(order.getId())
        );
    }


    @Transactional
    public OrderDTO modifyOrder(UUID orderId, ModifyOrderDTO modifyOrderRequest) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        orderValidator.validateModifiableOrder(order);

        List<OrderItem> updatedItems = orderItemService.modifyOrderItems(order, modifyOrderRequest);
        orderChargeService.updateServiceCharges(modifyOrderRequest.getServiceCharges(), order);

        Order savedOrder = orderRepository.save(order);
        BigDecimal originalPrice = orderItemService.calculateOriginalPrice(updatedItems);

        return OrderMapper.mapToOrderResponse(
                savedOrder, orderItemService.mapToOrderItemResponses(updatedItems),
                originalPrice, OrderHelper.determineCurrency(updatedItems),
                orderChargeService.findAppliedServiceChargesByOrderId(order.getId()));
    }

    public OrderDTO getOrderReceipt(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

        BigDecimal originalPrice = OrderHelper.calculateOriginalPrice(items, orderItemModifierRepository);
        Currency currency = OrderHelper.determineCurrency(items);

        List<OrderItemDTO> itemResponses = items.stream().map(item -> {
            List<OrderItemModifier> modifiers = orderItemModifierRepository.findByOrderItemId(item.getId());
            return OrderMapper.mapToOrderItemResponse(item, modifiers);
        }).collect(Collectors.toList());

        return OrderMapper.mapToOrderResponse(order, itemResponses, originalPrice, currency, appliedServiceChargeRepository.findByOrderId(order.getId()));
    }
}