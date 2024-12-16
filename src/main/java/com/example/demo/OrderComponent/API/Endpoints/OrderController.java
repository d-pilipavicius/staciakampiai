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
@RequestMapping("/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderApplicationService orderApplicationService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO createOrderDTO) {
        OrderDTO response = orderApplicationService.createOrder(createOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("order", response));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOrders(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int pageSize) {
        Page<OrderDTO> ordersPage = orderApplicationService.getOrders(page, pageSize);

        Map<String, Object> response = Map.of(
                "totalItems", ordersPage.getTotalElements(),
                "totalPages", ordersPage.getTotalPages(),
                "currentPage", page,
                "orders", ordersPage.getContent()
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable UUID orderId) {
        OrderDTO orderDTO = orderApplicationService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("order", orderDTO));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> modifyOrder(@PathVariable UUID orderId, @RequestBody ModifyOrderDTO modifyOrderRequest) {
        OrderDTO updatedOrder = orderApplicationService.modifyOrder(orderId, modifyOrderRequest);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("order", updatedOrder));
    }

    @GetMapping("/{orderId}/receipt")
    public ResponseEntity<?> getOrderReceipt(@PathVariable UUID orderId) {
        OrderDTO orderReceipt = orderApplicationService.getOrderReceipt(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("orderReceipt", orderReceipt));
    }
}