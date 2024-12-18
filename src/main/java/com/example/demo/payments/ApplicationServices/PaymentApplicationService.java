package com.example.demo.payments.ApplicationServices;

import com.example.demo.OrderComponent.ApplicationServices.OrderApplicationService;
import com.example.demo.payments.API.DTOs.*;
import com.example.demo.payments.Domain.Entities.Refund;
import com.example.demo.payments.Domain.Entities.Tip;
import com.example.demo.payments.Domain.Entities.Enums.PaymentMethod;
import com.example.demo.payments.Domain.Services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentApplicationService {

    private final OrderApplicationService orderApplicationService;
    private PaymentService paymentService;

    public Object createPayment(CreatePaymentDTO request, PaymentMethod paymentMethod) {
        orderApplicationService.validateOrder(request.getOrderId());

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItemPaymentDTO orderItem : request.getOrderItems()) {
            BigDecimal itemPrice = orderApplicationService.calculateItemPrice(orderItem.getOrderItemId(), orderItem.getQuantity());
            totalPrice = totalPrice.add(itemPrice);
        }

        request.setAmount(totalPrice);

        return paymentService.createPaymentsForOrderItems(request, paymentMethod);
    }

    public Refund initiateRefund(UUID paymentId) {
        return paymentService.refundPayment(paymentId);
    }

    public boolean completeOrderPayment(UUID orderId) {
        orderApplicationService.validateOrder(orderId);
        return paymentService.completeOrderPayment(orderId);
    }

    public Tip addTip(UUID businessId,AddTipDTO request) {
        orderApplicationService.validateOrder(request.getOrderId());
        return paymentService.addTip(businessId, request);
    }

    public Page<Tip> getOrderTips(UUID businessId, UUID orderId, int page, int pageSize) {
        orderApplicationService.validateOrder(orderId);
        return paymentService.getOrderTips(businessId, orderId, page, pageSize);
    }
}