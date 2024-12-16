package com.example.demo.payments.ApplicationServices;

import com.example.demo.OrderComponent.ApplicationServices.OrderApplicationService;
import com.example.demo.OrderComponent.Domain.Entities.OrderItem;
import com.example.demo.OrderComponent.Helpers.OrderHelper;
import com.example.demo.OrderComponent.Repositories.IOrderItemModifierRepository;
import com.example.demo.OrderComponent.Repositories.IOrderItemRepository;
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

    public Object createCardPayment(CreatePaymentDTO request) {
        orderApplicationService.validateOrder(request.getOrderId());
        orderApplicationService.validateOrderItems(request.getOrderId(), request.getOrderItems());

        BigDecimal totalPrice = orderApplicationService.calculateTotalPrice(request.getOrderId());
        request.setAmount(totalPrice);

        return paymentService.createPaymentsForOrderItems(request, PaymentMethod.CARD);
    }

    public Object createCashPayment(CreatePaymentDTO request) {
        orderApplicationService.validateOrder(request.getOrderId());
        orderApplicationService.validateOrderItems(request.getOrderId(), request.getOrderItems());

        BigDecimal totalPrice = orderApplicationService.calculateTotalPrice(request.getOrderId());
        request.setAmount(totalPrice);

        return paymentService.createPaymentsForOrderItems(request, PaymentMethod.CASH);
    }

    public Refund initiateRefund(UUID paymentId) {
        return paymentService.refundPayment(paymentId);
    }

    public boolean completeOrderPayment(UUID orderId) {
        orderApplicationService.validateOrder(orderId);
        return paymentService.completeOrderPayment(orderId);
    }

    public Tip addTip(AddTipDTO request) {
        orderApplicationService.validateOrder(request.getOrderId());
        return paymentService.addTip(request);
    }

    public Page<Tip> getOrderTips(UUID orderId, int page, int pageSize) {
        orderApplicationService.validateOrder(orderId);
        return paymentService.getOrderTips(orderId, page, pageSize);
    }
}