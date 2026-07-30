package com.tech.aop.services;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public void placeOrder() {
        System.out.println("Order placed successfully");
    }
}
