package com.example.demo.OrderComponent.API.Endpoints;

import com.example.demo.OrderComponent.API.DTOs.ModifyOrderDTO;
import com.example.demo.OrderComponent.API.DTOs.OrderDTO;
import com.example.demo.OrderComponent.ApplicationServices.OrderApplicationService;
import jakarta.persistence.EntityNotFoundException;
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

    @NotNull
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO createOrderDTO) {
        try {
            OrderDTO response = orderApplicationService.createOrder(createOrderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("order", response));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Related entity not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An unexpected error occurred"));
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOrders(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int pageSize) {
        try {
            Page<OrderDTO> ordersPage = orderApplicationService.getOrders(page, pageSize);

            Map<String, Object> response = Map.of(
                    "totalItems", ordersPage.getTotalElements(),
                    "totalPages", ordersPage.getTotalPages(),
                    "currentPage", page,
                    "orders", ordersPage.getContent()
            );

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An unexpected error occurred"));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable UUID orderId) {
        try {
            OrderDTO orderDTO = orderApplicationService.getOrderById(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("order", orderDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Order not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An unexpected error occurred"));
        }
    }

    @NotNull
    @PutMapping("/{orderId}")
    public ResponseEntity<?> modifyOrder(@PathVariable UUID orderId, @RequestBody ModifyOrderDTO modifyOrderRequest) {
        try {
            OrderDTO updatedOrder = orderApplicationService.modifyOrder(orderId, modifyOrderRequest);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("order", updatedOrder));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Order not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An unexpected error occurred"));
        }
    }
    
    @GetMapping("/{orderId}/receipt")
    public ResponseEntity<?> getOrderReceipt(@PathVariable UUID orderId) {
        try {
            OrderDTO orderReceipt = orderApplicationService.getOrderReceipt(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("orderReceipt", orderReceipt));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Order not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An unexpected error occurred"));
        }
    }
}