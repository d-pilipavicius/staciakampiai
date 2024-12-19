package com.example.demo.OrderComponent.ApplicationServices;

import com.example.demo.OrderComponent.API.DTOs.*;
import com.example.demo.OrderComponent.Domain.Entities.Enums.OrderStatus;
import com.example.demo.OrderComponent.Domain.Entities.Order;
import com.example.demo.OrderComponent.Domain.Entities.OrderItem;
import com.example.demo.OrderComponent.Domain.Services.OrderService;
import com.example.demo.OrderComponent.Helpers.Mappers.OrderMapper;
import com.example.demo.payments.API.DTOs.OrderItemPaymentDTO;
import com.example.demo.productComponent.applicationServices.ProductApplicationService;
import com.example.demo.serviceChargeComponent.applicationServices.ServiceChargeApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderApplicationService {
    private final OrderService orderService;
    private final ProductApplicationService productApplicationService;
    private final ServiceChargeApplicationService serviceChargeApplicationService;

    @Transactional
    public OrderDTO createOrder(OrderDTO createOrderDTO) {
        createOrderDTO.getItems().forEach(this::setProductAndModifierDetails);
        if (createOrderDTO.getServiceChargeIds() != null) {
            List<AppliedServiceChargeDTO> serviceCharges = createOrderDTO.getServiceChargeIds().stream()
                    .map(serviceChargeApplicationService::getServiceChargeById)
                    .map(OrderMapper::mapToAppliedServiceChargeDTO)
                    .collect(Collectors.toList());
            createOrderDTO.setServiceCharges(serviceCharges);
        }

        // adjust added item and modifier stock
        if (createOrderDTO.getItems() != null) {
            createOrderDTO.getItems().forEach(item -> {
                productApplicationService.decrementProductStock(item.getProductId(), item.getQuantity());
                if (item.getSelectedModifierIds() != null) {
                    item.getSelectedModifierIds().forEach(modifierId -> {
                        productApplicationService.decrementModifierStock(modifierId, item.getQuantity());
                    });
                }
            });
        }

        return orderService.createOrder(createOrderDTO);
    }

    public OrderDTO getOrderById(UUID orderId) {
        return orderService.getOrderById(orderId);
    }

    public Page<OrderDTO> getOrders(int page, int pageSize) {
        return orderService.getOrders(page, pageSize);
    }

    @Transactional
    public OrderDTO modifyOrder(UUID orderId, ModifyOrderDTO modifyOrderRequest) {
        modifyOrderRequest.getItems().forEach(this::setProductAndModifierDetails);
        if (modifyOrderRequest.getServiceChargeIds() != null) {
            List<AppliedServiceChargeDTO> serviceCharges = modifyOrderRequest.getServiceChargeIds().stream()
                    .map(serviceChargeApplicationService::getServiceChargeById)
                    .map(OrderMapper::mapToAppliedServiceChargeDTO)
                    .collect(Collectors.toList());
            modifyOrderRequest.setServiceCharges(serviceCharges);
        }

        // adjust modified item and modifier stock
        OrderDTO oldOrder = orderService.getOrderById(orderId);

        if(oldOrder.getStatus().equals(OrderStatus.CANCELED) || oldOrder.getStatus().equals(OrderStatus.CLOSED)) {
            throw new IllegalArgumentException("Cannot modify a canceled or closed order");
        }

        if (modifyOrderRequest.getItems() != null && !modifyOrderRequest.getStatus().equals(OrderStatus.CANCELED)) {
            modifyOrderRequest.getItems().forEach(item -> {
                // Item is added
                if (oldOrder.getItems().stream().noneMatch(oldItem -> oldItem.getProductId().equals(item.getProductId()))) {
                    productApplicationService.decrementProductStock(item.getProductId(), item.getQuantity());
                    if (item.getSelectedModifierIds() != null) {
                        item.getSelectedModifierIds().forEach(modifierId -> {
                            productApplicationService.decrementModifierStock(modifierId, item.getQuantity());
                        });
                    }
                } else {
                    // Item is modified
                    oldOrder.getItems().forEach(oldItem -> {
                        if (oldItem.getProductId().equals(item.getProductId())) {
                            int oldQuantity = oldItem.getQuantity();
                            int newQuantity = item.getQuantity();

                            if (oldQuantity > newQuantity) {
                                productApplicationService.incrementProductStock(oldItem.getProductId(), oldQuantity - newQuantity);
                            } else if (oldQuantity < newQuantity) {
                                productApplicationService.decrementProductStock(oldItem.getProductId(), newQuantity - oldQuantity);
                            }

                            if (oldItem.getSelectedModifierIds() != null) {
                                List<UUID> oldModifiers = oldItem.getSelectedModifierIds();
                                List<UUID> newModifiers = item.getSelectedModifierIds();

                                // Increment stock for removed modifiers
                                oldModifiers.stream()
                                        .filter(modifierId -> newModifiers == null || !newModifiers.contains(modifierId))
                                        .forEach(modifierId -> {
                                            productApplicationService.incrementModifierStock(modifierId, oldQuantity);
                                        });

                                // Decrement stock for added modifiers
                                if (newModifiers != null) {
                                    newModifiers.stream()
                                            .filter(modifierId -> !oldModifiers.contains(modifierId))
                                            .forEach(modifierId -> {
                                                productApplicationService.decrementModifierStock(modifierId, newQuantity);
                                            });
                                }
                            }
                        }
                    });
                }
            });

            // Item is removed
            oldOrder.getItems().forEach(oldItem -> {
                if (modifyOrderRequest.getItems().stream().noneMatch(item -> item.getProductId().equals(oldItem.getProductId()))) {
                    productApplicationService.incrementProductStock(oldItem.getProductId(), oldItem.getQuantity());
                    if (oldItem.getSelectedModifierIds() != null) {
                        oldItem.getSelectedModifierIds().forEach(modifierId -> {
                            productApplicationService.incrementModifierStock(modifierId, 1);
                        });
                    }
                }
            });
        }

        // adjust stock if the order was canceled
        if (modifyOrderRequest.getStatus() != null && modifyOrderRequest.getStatus().equals(OrderStatus.CANCELED)) {
            oldOrder.getItems().forEach(item -> {
                if(item.getModifiers() != null){
                    List<UUID> modifierIds = item.getModifiers().stream()
                            .map(OrderItemModifierDTO::getId)
                            .toList();

                    productApplicationService.incrementProductStock(item.getProductId(), item.getQuantity());
                    modifierIds.forEach(modifierId -> {
                        productApplicationService.incrementModifierStock(modifierId, item.getQuantity());
                    });
                }
            });
        }

        return orderService.modifyOrder(orderId, modifyOrderRequest);
    }
    public OrderDTO getOrderReceipt(UUID orderId) {
        OrderDTO orderDTO = orderService.getOrderReceipt(orderId);
        return OrderMapper.mapToOrderReceipt(orderDTO).build();
    }

    private void setProductAndModifierDetails(OrderItemDTO itemRequest) {
        var product = productApplicationService.getProductById(itemRequest.getProductId());
        itemRequest.setTitle(product.getTitle());
        itemRequest.setUnitPrice(OrderItemDTO.UnitPrice.builder()
                .base(product.getPrice().getAmount())
                .withModifiers(product.getPrice().getAmount())
                .build());
        itemRequest.setCurrency(product.getPrice().getCurrency());

        if (itemRequest.getSelectedModifierIds() != null) {
            var modifiers = productApplicationService.getModifiersByProductId(itemRequest.getProductId()).getItems();
            var selectedModifiers = modifiers.stream()
                    .filter(modifier -> itemRequest.getSelectedModifierIds().contains(modifier.getId()))
                    .collect(Collectors.toList());
            itemRequest.setModifiers(OrderMapper.mapToOrderItemModifierResponse(selectedModifiers));
        }
    }

    // Payment related methods
    public void validateOrder(UUID orderId) {
        orderService.validateOrder(orderId);
    }

    public void validateOrderItems(UUID orderId, List<OrderItemPaymentDTO> orderItems) {
        orderService.validateOrderItems(orderId, orderItems);
    }

    public BigDecimal calculateItemPrice(UUID orderItemId, Integer quantity) {
        return orderService.calculateItemPrice(orderItemId, quantity);
    }

    public OrderDTO returnOrder(UUID orderId) {
        return orderService.returnOrder(orderId);
    }
}