package com.example.demo.OrderComponent.API.Endpoints;

import com.example.demo.OrderComponent.API.DTOs.ModifyOrderDTO;
import com.example.demo.OrderComponent.API.DTOs.OrderDTO;
import com.example.demo.OrderComponent.ApplicationServices.OrderApplicationService;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/orders/{businessId}")
@AllArgsConstructor
public class OrderController {
    private final OrderApplicationService orderApplicationService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@NotNull @RequestBody OrderDTO createOrderDTO) {
        OrderDTO response = orderApplicationService.createOrder(createOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOrders(@RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<OrderDTO> ordersPage = orderApplicationService.getOrders(pageNumber, pageSize);

        Map<String, Object> response = Map.of(
                "totalItems", ordersPage.getTotalElements(),
                "totalPages", ordersPage.getTotalPages(),
                "currentPage", pageNumber,
                "orders", ordersPage.getContent());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@NotNull @PathVariable UUID orderId) {
        OrderDTO orderDTO = orderApplicationService.getOrderById(orderId);
        return ResponseEntity.ok(orderDTO);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> modifyOrder(@NotNull @PathVariable UUID orderId,
            @RequestBody ModifyOrderDTO modifyOrderRequest) {
        OrderDTO updatedOrder = orderApplicationService.modifyOrder(orderId, modifyOrderRequest);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{orderId}/receipt")
    public ResponseEntity<OrderDTO> getOrderReceipt(@NotNull @PathVariable UUID orderId) {
        OrderDTO orderReceipt = orderApplicationService.getOrderReceipt(orderId);
        return ResponseEntity.ok(orderReceipt);
    }
}