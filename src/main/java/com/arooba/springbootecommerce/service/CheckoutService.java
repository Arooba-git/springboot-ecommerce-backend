package com.arooba.springbootecommerce.service;

import com.arooba.springbootecommerce.dto.PaymentInfo;
import com.arooba.springbootecommerce.dto.Purchase;
import com.arooba.springbootecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
