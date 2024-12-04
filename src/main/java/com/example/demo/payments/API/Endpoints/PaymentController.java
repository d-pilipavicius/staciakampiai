package com.example.demo.payments.API.Endpoints;

import com.example.demo.payments.API.DTOs.*;
import com.example.demo.payments.Domain.Entities.Enums.PaymentStatus;
import com.example.demo.payments.Helpers.Mappers;
import com.example.demo.payments.Domain.Entities.Refund;
import com.example.demo.payments.Domain.Entities.Enums.PaymentMethod;
import com.example.demo.payments.Domain.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.payments.Domain.Entities.Tip;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/tips")
    public ResponseEntity<?> addTip(@RequestBody AddTipDTO request) {
        Tip createdTip = paymentService.addTip(request);
        return ResponseEntity.status(201).body(Map.of("tip", createdTip));
    }

    @GetMapping("/tips")
    public ResponseEntity<Map<String, Object>> getOrderTips(
            @RequestParam UUID orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Tip> tipsPage = paymentService.getOrderTips(orderId, page, pageSize);
        Map<String, Object> response = Mappers.mapPageToResponse(tipsPage);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/card")
    public ResponseEntity<Object> createCardPayment(@RequestBody CreatePaymentDTO request) {
        Object paymentResponse = paymentService.createPaymentsForOrderItems(request, PaymentMethod.CARD);
        return ResponseEntity.status(201).body(Map.of("checkoutSession", paymentResponse));
    }

    @PostMapping("/cash")
    public ResponseEntity<Object> createCashPayment(@RequestBody CreatePaymentDTO request) {
        Object paymentResponse = paymentService.createPaymentsForOrderItems(request, PaymentMethod.CASH);
        return ResponseEntity.status(201).body(Map.of("payment", paymentResponse));
    }

    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<?> initiateRefund(@PathVariable UUID paymentId) {
        Refund refund = paymentService.refundPayment(paymentId);

        Map<String, Object> refundResponse = Mappers.toRefundResponse(refund);

        return ResponseEntity.status(201).body(Map.of("refund", refundResponse));
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completeOrderPayment(@RequestParam UUID orderId) {
        boolean success = paymentService.completeOrderPayment(orderId);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400).body("Failed to close the order.");
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getPaymentIdAndStatus(@RequestParam UUID orderId) {
        List<Map<String, Object>> paymentStatuses = paymentService.getPaymentStatus(orderId);
        return ResponseEntity.ok(Map.of("Payments", paymentStatuses));
    }
}
