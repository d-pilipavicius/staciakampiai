package com.example.demo.payments.ApplicationServices;

import com.example.demo.payments.API.DTOs.*;
import com.example.demo.payments.Domain.Entities.Refund;
import com.example.demo.payments.Domain.Entities.Tip;
import com.example.demo.payments.Domain.Entities.Enums.PaymentMethod;
import com.example.demo.payments.Domain.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentApplicationService {

    @Autowired
    private PaymentService paymentService;

    public Object createCardPayment(CreatePaymentDTO request) {
        return paymentService.createPaymentsForOrderItems(request, PaymentMethod.CARD);
    }

    public Object createCashPayment(CreatePaymentDTO request) {
        return paymentService.createPaymentsForOrderItems(request, PaymentMethod.CASH);
    }

    public Refund initiateRefund(UUID paymentId) {
        return paymentService.refundPayment(paymentId);
    }

    public boolean completeOrderPayment(UUID orderId) {
        return paymentService.completeOrderPayment(orderId);
    }

    public Tip addTip(AddTipDTO request) {
        return paymentService.addTip(request);
    }

    public Page<Tip> getOrderTips(UUID orderId, int page, int pageSize) {
        return paymentService.getOrderTips(orderId, page, pageSize);
    }
}