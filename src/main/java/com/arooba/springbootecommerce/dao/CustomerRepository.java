package com.arooba.springbootecommerce.dao;

import com.arooba.springbootecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String emailTiFind);
}
