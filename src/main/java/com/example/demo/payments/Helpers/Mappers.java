package com.example.demo.payments.Helpers;

import com.example.demo.payments.Domain.Entities.Payment;
import com.example.demo.payments.Domain.Entities.Refund;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Mappers {
    public static <T> Map<String, Object> mapPageToResponse(Page<T> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        response.put("currentPage", page.getNumber() + 1);  // Convert 0-based to 1-based
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

    public static Map<String, Object> toPaymentStatusResponse(Payment payment) {
        Map<String, Object> paymentStatusMap = new HashMap<>();
        paymentStatusMap.put("paymentId", payment.getId());
        paymentStatusMap.put("status", payment.getStatus());
        return paymentStatusMap;
    }
}
