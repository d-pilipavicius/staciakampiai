package com.example.demo.payments.Domain.Services;

import com.example.demo.payments.API.DTOs.*;
import com.example.demo.payments.Helpers.Mappers;
import com.stripe.model.checkout.Session;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.payments.Domain.Entities.OrderItemPayment;
import com.example.demo.payments.Domain.Entities.Payment;
import com.example.demo.payments.Domain.Entities.Refund;
import com.example.demo.payments.Domain.Entities.Tip;
import com.example.demo.payments.Domain.Entities.Enums.PaymentMethod;
import com.example.demo.payments.Domain.Entities.Enums.PaymentStatus;
import com.example.demo.payments.Repositories.IOrderItemPaymentRepository;
import com.example.demo.payments.Repositories.IPaymentRepository;
import com.example.demo.payments.Repositories.IRefundRepository;
import com.example.demo.payments.Repositories.ITipRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentService {
    private final IPaymentRepository IPaymentRepository;
    private final IRefundRepository IRefundRepository;
    private final IOrderItemPaymentRepository IOrderItemPaymentRepository;
    private final ITipRepository ITipRepository;

    private final StripeService stripeService;

    public Object createPaymentsForOrderItems(CreatePaymentDTO request, PaymentMethod paymentMethod) {
        BigDecimal totalAmount = request.getAmount();
        long totalAmountInCents = request.getAmount().multiply(BigDecimal.valueOf(100)).longValue();

        Payment payment = Mappers.toPayment(request, totalAmount, paymentMethod);
        payment = IPaymentRepository.save(payment);

        List<OrderItemPayment> orderItemPayments = Mappers.toOrderItemPayments(request, payment.getId());
        IOrderItemPaymentRepository.saveAll(orderItemPayments);

        List<OrderItemPaymentDTO> orderItems = Mappers.mapToOrderItemPaymentDTOs(orderItemPayments);

        if (paymentMethod == PaymentMethod.CASH) {
            return Mappers.toPaymentResponseDTO(payment, orderItems);
        } else {
            try {
                Session session = stripeService.createStripeSession(request.getCurrency(),totalAmountInCents, request.getOrderId().toString());

                payment.setPaymentProcessorId(session.getId());
                payment.setStatus(PaymentStatus.PENDING);
                IPaymentRepository.save(payment);

                return Mappers.toCheckoutSessionDTO(session.getUrl(), payment);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create Stripe PaymentIntent", e);
            }
        }
    }

    public Refund refundPayment(UUID paymentId) {
        Payment payment = IPaymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getMethod() == PaymentMethod.CARD) {
            try {
                String paymentIntentId = stripeService.getPaymentIntentIdFromSession(payment.getPaymentProcessorId());
                stripeService.issueRefund(paymentIntentId, payment.getAmount().multiply(BigDecimal.valueOf(100)).longValue());
            } catch (Exception e) {
                throw new RuntimeException("Failed to issue Stripe refund", e);
            }
        }

        Refund refund = Mappers.toRefund(payment, paymentId);
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

        payments.forEach(payment -> {
            payment.setStatus(PaymentStatus.SUCCEEDED);
            IPaymentRepository.save(payment);
        });
        return true;
    }

    public Tip addTip(UUID businessId, AddTipDTO request) {
        Tip tipPayment = Mappers.toTip(request);
        tipPayment.setBusinessId(businessId);
        ITipRepository.save(tipPayment);
        return tipPayment;
    }

    public GetTipsDTO getTips(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Tip> tipsPage = ITipRepository.findAll(pageable);
        return Mappers.toGetTipsDTO(tipsPage);
    }

    public void processCheckoutSessionCompleted(CheckoutSessionCompletedDTO request) {
        Payment payment = IPaymentRepository.findByTransactionId(request.getTransactionId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.SUCCEEDED);
        IPaymentRepository.save(payment);
    }
}

