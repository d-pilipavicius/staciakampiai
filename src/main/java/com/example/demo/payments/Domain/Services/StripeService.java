package com.example.demo.payments.Domain.Services;

import com.stripe.model.checkout.Session;
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
}