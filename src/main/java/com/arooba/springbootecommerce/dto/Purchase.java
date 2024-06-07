package com.arooba.springbootecommerce.dto;

import com.arooba.springbootecommerce.entity.Address;
import com.arooba.springbootecommerce.entity.Customer;
import com.arooba.springbootecommerce.entity.Order;
import com.arooba.springbootecommerce.entity.OrderItem;
import lombok.Data;
import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address billingAddress;
    private Address shippingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
