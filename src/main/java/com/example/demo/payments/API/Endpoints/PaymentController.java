package com.example.demo.payments.API.Endpoints;

import com.example.demo.payments.API.DTOs.*;
import com.example.demo.payments.Domain.Entities.Enums.PaymentMethod;
import com.example.demo.payments.Helpers.Mappers;
import com.example.demo.payments.Domain.Entities.Refund;
import com.example.demo.payments.ApplicationServices.PaymentApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.payments.Domain.Entities.Tip;

import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/payments/{businessId}")
public class PaymentController {
    private PaymentApplicationService paymentApplicationService;

    @PostMapping("/tips")
    public ResponseEntity<Tip> addTip(@PathVariable UUID businessId, @RequestBody AddTipDTO request) {
        Tip createdTip = paymentApplicationService.addTip(businessId, request);
        return ResponseEntity.ok(createdTip);
    }

    @GetMapping("/tips")
    public ResponseEntity<Map<String, Object>> getOrderTips(
            @PathVariable UUID businessId,
            @RequestParam UUID orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Tip> tipsPage = paymentApplicationService.getOrderTips(businessId, orderId, page, pageSize);
        Map<String, Object> response = Mappers.mapPageToResponse(tipsPage);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/card")
    public ResponseEntity<Object> createCardPayment(@RequestBody CreatePaymentDTO request) {
        Object paymentResponse = paymentApplicationService.createPayment(request, PaymentMethod.CARD);
        return ResponseEntity.ok(paymentResponse);
    }

    @PostMapping("/cash")
    public ResponseEntity<Object> createCashPayment(@RequestBody CreatePaymentDTO request) {
        Object paymentResponse = paymentApplicationService.createPayment(request, PaymentMethod.CASH);
        return ResponseEntity.ok(paymentResponse);
    }

    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<Map<String, Object>> initiateRefund(@PathVariable UUID paymentId) {
        Refund refund = paymentApplicationService.initiateRefund(paymentId);
        Map<String, Object> refundResponse = Mappers.toRefundResponse(refund);
        return ResponseEntity.ok(refundResponse);
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completeOrderPayment(@RequestParam UUID orderId) {
        boolean success = paymentApplicationService.completeOrderPayment(orderId);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400).body("Failed to close the order.");
        }
    }
}