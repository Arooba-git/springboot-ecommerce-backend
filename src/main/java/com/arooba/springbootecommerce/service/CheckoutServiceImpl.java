package com.arooba.springbootecommerce.service;

import com.arooba.springbootecommerce.dao.CustomerRepository;
import com.arooba.springbootecommerce.dto.PaymentInfo;
import com.arooba.springbootecommerce.entity.Customer;
import com.arooba.springbootecommerce.entity.Order;
import com.arooba.springbootecommerce.entity.OrderItem;
import com.arooba.springbootecommerce.dto.Purchase;
import com.arooba.springbootecommerce.dto.PurchaseResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private CustomerRepository customerRepository;
    public CheckoutServiceImpl(CustomerRepository customerRepository, @Value("${stripe.key.secret}") String secretKey) {
        this.customerRepository = customerRepository;
        Stripe.apiKey = secretKey;
    }


    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();

        String trackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(trackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        Customer customer = purchase.getCustomer();

        String customerEamil = customer.getEmail();
        Customer existingCustomer = customerRepository.findByEmail(customerEamil);

        // if already exists
        if (existingCustomer != null) {
            // assign this one
            customer = existingCustomer;
        }

        System.out.println("purchase here: " + purchase);
        System.out.println("order here: " + order);
        customer.add(order);

        customerRepository.save(customer);

        return new PurchaseResponse(trackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("receipt_email", paymentInfo.getReceiptEmail());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "luv2shop purchase");


        System.out.println("\nparams: " + params);
        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }


}
