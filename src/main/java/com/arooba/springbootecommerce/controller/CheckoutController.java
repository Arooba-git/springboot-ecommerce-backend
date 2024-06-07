package com.arooba.springbootecommerce.controller;

import com.arooba.springbootecommerce.dto.PaymentInfo;
import com.arooba.springbootecommerce.dto.Purchase;
import com.arooba.springbootecommerce.dto.PurchaseResponse;
import com.arooba.springbootecommerce.service.CheckoutService;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin
public class CheckoutController {
    private CheckoutService checkoutService;
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        PurchaseResponse purchaseResponse = this.checkoutService.placeOrder(purchase);
        return purchaseResponse;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        String payment = checkoutService.createPaymentIntent(paymentInfo).toJson();
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
}
