package com.example.demo.payments.ApplicationServices;

import com.example.demo.payments.API.DTOs.*;
import com.example.demo.payments.Domain.Entities.Refund;
import com.example.demo.payments.Domain.Entities.Tip;
import com.example.demo.payments.Domain.Entities.Enums.PaymentMethod;
import com.example.demo.payments.Domain.Services.PaymentService;
import com.example.demo.OrderComponent.Validators.OrderValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentApplicationService {

    private final OrderValidator orderValidator;
    private PaymentService paymentService;

    public Object createCardPayment(CreatePaymentDTO request) {
        orderValidator.isValidOrder(request.getOrderId());
        request.getOrderItems().forEach(orderItem -> orderValidator.isValidOrderItem(request.getOrderId(), orderItem.getOrderItemId()));
        return paymentService.createPaymentsForOrderItems(request, PaymentMethod.CARD);
    }

    public Object createCashPayment(CreatePaymentDTO request) {
        orderValidator.isValidOrder(request.getOrderId());
        request.getOrderItems().forEach(orderItem -> orderValidator.isValidOrderItem(request.getOrderId(), orderItem.getOrderItemId()));
        return paymentService.createPaymentsForOrderItems(request, PaymentMethod.CASH);
    }

    public Refund initiateRefund(UUID paymentId) {
        return paymentService.refundPayment(paymentId);
    }

    public boolean completeOrderPayment(UUID orderId) {
        orderValidator.isValidOrder(orderId);
        return paymentService.completeOrderPayment(orderId);
    }

    public Tip addTip(AddTipDTO request) {
        orderValidator.isValidOrder(request.getOrderId());
        return paymentService.addTip(request);
    }

    public Page<Tip> getOrderTips(UUID orderId, int page, int pageSize) {
        orderValidator.isValidOrder(orderId);
        return paymentService.getOrderTips(orderId, page, pageSize);
    }
}