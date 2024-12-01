package com.example.demo.payments.domain.services;

import com.example.demo.payments.API.DTOs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.payments.domain.entities.OrderItemPayment;
import com.example.demo.payments.domain.entities.Payment;
import com.example.demo.payments.domain.entities.Refund;
import com.example.demo.payments.domain.entities.Tip;
import com.example.demo.payments.domain.entities.enums.PaymentMethod;
import com.example.demo.payments.domain.entities.enums.PaymentRefundStatus;
import com.example.demo.payments.domain.entities.enums.PaymentStatus;
import com.example.demo.payments.repository.OrderItemPaymentRepository;
import com.example.demo.payments.repository.PaymentRepository;
import com.example.demo.payments.repository.RefundRepository;
import com.example.demo.payments.repository.TipRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RefundRepository refundRepository;
    @Autowired
    private final OrderItemPaymentRepository orderItemPaymentRepository;
    @Autowired
    private TipRepository tipRepository;

    public Object createPaymentsForOrderItems(CreatePaymentDTO request, PaymentMethod paymentMethod) {
        List<OrderItemPaymentDTO> orderItemPayments = new ArrayList<>();

        BigDecimal totalAmount = request.getOrderItems().stream()
                .map(item -> item.getAmount().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .businessId(request.getBusinessId())
                .employeeId(request.getEmployeeId())
                .amount(totalAmount)
                .currency(request.getCurrency())
                .method(paymentMethod)
                .status(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        payment = paymentRepository.save(payment);

        for (OrderItemPaymentDTO item : request.getOrderItems()) {
            OrderItemPayment orderItemPayment = OrderItemPayment.builder()
                    .orderItemId(item.getOrderItemId())
                    .quantity(item.getQuantity())
                    .paymentId(payment.getId())
                    .build();
            orderItemPaymentRepository.save(orderItemPayment);
        }

        List<OrderItemPaymentDTO> orderItems = request.getOrderItems().stream()
                .map(item -> new OrderItemPaymentDTO(item.getOrderItemId(), item.getAmount(), item.getQuantity()))
                .collect(Collectors.toList());

        if (paymentMethod == PaymentMethod.CASH) {
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
        } else {
            String checkoutUrl = "https://www.clickheretocompletecheckout.com/" + payment.getId();
            return new CheckoutSessionDTO(payment.getId(), checkoutUrl);
        }
    }

    public Refund refundPayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Refund refund = new Refund();
        refund.setId(UUID.randomUUID());
        refund.setPaymentId(paymentId);
        refund.setAmount(payment.getAmount());
        refund.setCurrency(payment.getCurrency());
        refund.setBusinessId(payment.getBusinessId());
        refund.setEmployeeId(payment.getEmployeeId());
        refund.setStatus(PaymentRefundStatus.SUCCEEDED);
        refund.setCreatedAt(LocalDateTime.now());

        Refund savedRefund = refundRepository.save(refund);

        payment.setStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);

        return savedRefund;
    }

    public boolean completeOrderPayment(UUID orderId) {
        List<Payment> payments = paymentRepository.findByOrderId(orderId);
        if (payments.isEmpty()) {
            return false;
        }

        for (Payment payment : payments) {
            payment.setStatus(PaymentStatus.SUCCEEDED);
            paymentRepository.save(payment);
        }
        return true;
    }

    public Tip addTip(AddTipDTO request) {
        Tip tipPayment = new Tip();
        tipPayment.setOrderId(request.getOrderId());
        tipPayment.setEmployeeId(request.getEmployeeId());
        tipPayment.setAmount(request.getAmount());
        tipPayment.setCurrency(request.getCurrency());
        tipRepository.save(tipPayment);
        return tipPayment;
    }

    public PaymentService(OrderItemPaymentRepository orderItemPaymentRepository) {
        this.orderItemPaymentRepository = orderItemPaymentRepository;
    }

    public Page<Tip> getOrderTips(UUID orderId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return tipRepository.findByOrderId(orderId, pageable);
    }
}
