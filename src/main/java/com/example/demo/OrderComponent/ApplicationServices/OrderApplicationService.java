package com.example.demo.OrderComponent.ApplicationServices;

import com.example.demo.OrderComponent.API.DTOs.OrderDTO;
import com.example.demo.OrderComponent.API.DTOs.ModifyOrderDTO;
import com.example.demo.OrderComponent.API.DTOs.OrderItemDTO;
import com.example.demo.OrderComponent.Domain.Services.OrderService;
import com.example.demo.OrderComponent.Helpers.Mappers.OrderMapper;
import com.example.demo.productComponent.applicationServices.ProductApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderApplicationService {
    private final OrderService orderService;
    private final ProductApplicationService productApplicationService;

    public OrderDTO createOrder(OrderDTO createOrderDTO) {
        createOrderDTO.getItems().forEach(this::setProductAndModifierDetails);
        return orderService.createOrder(createOrderDTO);
    }

    public OrderDTO getOrderById(UUID orderId) {
        return orderService.getOrderById(orderId);
    }

    public Page<OrderDTO> getOrders(int page, int pageSize) {
        return orderService.getOrders(page, pageSize);
    }

    public OrderDTO modifyOrder(UUID orderId, ModifyOrderDTO modifyOrderRequest) {
        modifyOrderRequest.getItems().forEach(this::setProductAndModifierDetails);
        return orderService.modifyOrder(orderId, modifyOrderRequest);
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

    public OrderDTO getOrderReceipt(UUID orderId) {
        OrderDTO orderDTO = orderService.getOrderReceipt(orderId);
        return OrderMapper.mapToOrderReceipt(orderDTO).build();
    }
}