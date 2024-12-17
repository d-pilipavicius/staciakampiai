package com.example.demo.payments.Domain.Services;

import com.stripe.model.Refund;
import com.stripe.model.checkout.Session;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    public Session createStripeSession(long totalAmountInCents, String orderId) throws Exception {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://your-success-url.com")
                .setCancelUrl("https://your-cancel-url.com")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(totalAmountInCents)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Order Payment")
                                        .build())
                                .build())
                        .setQuantity(1L)
                        .build())
                .putMetadata("orderId", orderId)
                .build();

        return Session.create(params);
    }

    public Refund issueRefund(String paymentIntentId, long amountInCents) throws Exception {
        RefundCreateParams params = RefundCreateParams.builder()
                .setPaymentIntent(paymentIntentId)
                .setAmount(amountInCents)
                .build();

        return Refund.create(params);
    }

    public String getPaymentIntentIdFromSession(String sessionId) throws Exception {
        Session session = Session.retrieve(sessionId);
        return session.getPaymentIntent();
    }
}