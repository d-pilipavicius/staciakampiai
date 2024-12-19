package com.example.demo.payments.Helpers;

import com.example.demo.payments.API.DTOs.*;
import com.example.demo.payments.Domain.Entities.Enums.PaymentMethod;
import com.example.demo.payments.Domain.Entities.Enums.PaymentRefundStatus;
import com.example.demo.payments.Domain.Entities.Enums.PaymentStatus;
import com.example.demo.payments.Domain.Entities.OrderItemPayment;
import com.example.demo.payments.Domain.Entities.Payment;
import com.example.demo.payments.Domain.Entities.Refund;
import com.example.demo.payments.Domain.Entities.Tip;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Mappers {

    public static Payment toPayment(CreatePaymentDTO request, BigDecimal totalAmount, PaymentMethod paymentMethod) {
        return Payment.builder()
                .orderId(request.getOrderId())
                .businessId(request.getBusinessId())
                .employeeId(request.getEmployeeId())
                .amount(totalAmount)
                .currency(request.getCurrency())
                .method(paymentMethod)
                .status(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Refund toRefund(Payment payment, UUID paymentId) {
        return Refund.builder()
                .businessId(payment.getBusinessId())
                .paymentId(paymentId)
                .employeeId(payment.getEmployeeId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(PaymentRefundStatus.SUCCEEDED)
                .createdAt(LocalDateTime.now())
                .build();
    }


    public static Tip toTip(AddTipDTO request) {
        return Tip.builder()
                .orderId(request.getOrderId())
                .employeeId(request.getEmployeeId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .build();
    }

    public static List<OrderItemPayment> toOrderItemPayments(CreatePaymentDTO request, UUID paymentId) {
        return request.getOrderItems().stream()
                .map(item -> OrderItemPayment.builder()
                        .orderItemId(item.getOrderItemId())
                        .quantity(item.getQuantity())
                        .paymentId(paymentId)
                        .build())
                .collect(Collectors.toList());
    }

    public static <T> Map<String, Object> mapPageToResponse(Page<T> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        response.put("currentPage", page.getNumber());  // Convert 0-based to 1-based
        response.put("items", page.getContent());
        return response;
    }

    public static Map<String, Object> toRefundResponse(Refund refund) {
        return Map.of(
                "id", refund.getId(),
                "paymentId", refund.getPaymentId(),
                "amount", refund.getAmount(),
                "currency", refund.getCurrency(),
                "refundStatus", refund.getStatus(),
                "createdAt", refund.getCreatedAt()
        );
    }

    public static PaymentResponseDTO toPaymentResponseDTO(Payment payment, List<OrderItemPaymentDTO> orderItems) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getOrderId(),
                orderItems,
                payment.getAmount(),
                payment.getCurrency(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getCreatedAt()
        );
    }

    public static CheckoutSessionDTO toCheckoutSessionDTO(String clientSecret, Payment payment) {
        return new CheckoutSessionDTO(payment.getId(), clientSecret);
    }

    public static List<OrderItemPaymentDTO> mapToOrderItemPaymentDTOs(List<OrderItemPayment> orderItemPayments) {
        return orderItemPayments.stream()
                .map(item -> new OrderItemPaymentDTO(
                        item.getOrderItemId(),
                        item.getQuantity()))
                .collect(Collectors.toList());
    }

    public static GetTipsDTO toGetTipsDTO(Page<Tip> tipsPage) {
        List<TipDTO> tipDTOs = tipsPage.getContent().stream()
                .map(tip -> new TipDTO(
                        tip.getId(),
                        tip.getOrderId(),
                        tip.getEmployeeId(),
                        tip.getAmount(),
                        tip.getCurrency(),
                        tip.getBusinessId()
                ))
                .collect(Collectors.toList());
        return GetTipsDTO.builder()
                .currentPage(tipsPage.getNumber())
                .totalItems((int) tipsPage.getTotalElements())
                .totalPages(tipsPage.getTotalPages())
                .items(tipDTOs)
                .build();
    }
}