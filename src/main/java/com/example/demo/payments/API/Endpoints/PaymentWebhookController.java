package com.example.demo.payments.API.Endpoints;

import com.example.demo.payments.API.DTOs.CheckoutSessionCompletedDTO;
import com.example.demo.payments.Domain.Services.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/payments/{businessId}/webhooks")
@AllArgsConstructor
public class PaymentWebhookController {
    private final PaymentService paymentService;

    @PostMapping("/checkout-session-completed")
    public ResponseEntity<String> handleCheckoutSessionCompleted(@NotNull @RequestBody @Valid CheckoutSessionCompletedDTO request) {
        paymentService.processCheckoutSessionCompleted(request);
        return ResponseEntity.ok("Event received successfully");
    }
}