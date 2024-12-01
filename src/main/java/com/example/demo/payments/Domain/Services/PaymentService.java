package com.example.demo.payments.Domain.Services;

import com.example.demo.payments.API.DTOs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.payments.Domain.Entities.OrderItemPayment;
import com.example.demo.payments.Domain.Entities.Payment;
import com.example.demo.payments.Domain.Entities.Refund;
import com.example.demo.payments.Domain.Entities.Tip;
import com.example.demo.payments.Domain.Entities.Enums.PaymentMethod;
import com.example.demo.payments.Domain.Entities.Enums.PaymentRefundStatus;
import com.example.demo.payments.Domain.Entities.Enums.PaymentStatus;
import com.example.demo.payments.Repositories.IOrderItemPaymentRepository;
import com.example.demo.payments.Repositories.IPaymentRepository;
import com.example.demo.payments.Repositories.IRefundRepository;
import com.example.demo.payments.Repositories.ITipRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private IPaymentRepository IPaymentRepository;
    @Autowired
    private IRefundRepository IRefundRepository;
    @Autowired
    private final IOrderItemPaymentRepository IOrderItemPaymentRepository;
    @Autowired
    private ITipRepository ITipRepository;

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

        payment = IPaymentRepository.save(payment);

        for (OrderItemPaymentDTO item : request.getOrderItems()) {
            OrderItemPayment orderItemPayment = OrderItemPayment.builder()
                    .orderItemId(item.getOrderItemId())
                    .quantity(item.getQuantity())
                    .paymentId(payment.getId())
                    .build();
            IOrderItemPaymentRepository.save(orderItemPayment);
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
        Payment payment = IPaymentRepository.findById(paymentId)
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

        Refund savedRefund = IRefundRepository.save(refund);

        payment.setStatus(PaymentStatus.REFUNDED);
        IPaymentRepository.save(payment);

        return savedRefund;
    }

    public boolean completeOrderPayment(UUID orderId) {
        List<Payment> payments = IPaymentRepository.findByOrderId(orderId);
        if (payments.isEmpty()) {
            return false;
        }

        for (Payment payment : payments) {
            payment.setStatus(PaymentStatus.SUCCEEDED);
            IPaymentRepository.save(payment);
        }
        return true;
    }

    public Tip addTip(AddTipDTO request) {
        Tip tipPayment = new Tip();
        tipPayment.setOrderId(request.getOrderId());
        tipPayment.setEmployeeId(request.getEmployeeId());
        tipPayment.setAmount(request.getAmount());
        tipPayment.setCurrency(request.getCurrency());
        ITipRepository.save(tipPayment);
        return tipPayment;
    }

    public PaymentService(IOrderItemPaymentRepository IOrderItemPaymentRepository) {
        this.IOrderItemPaymentRepository = IOrderItemPaymentRepository;
    }

    public Page<Tip> getOrderTips(UUID orderId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return ITipRepository.findByOrderId(orderId, pageable);
    }
}
