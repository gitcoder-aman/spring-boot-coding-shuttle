package com.tech.aop.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Test
    void processPayment(){
        paymentService.processPayment();
    }
    @Test
    void refundPayment(){
        paymentService.refundPayment();
    }
}
