package com.tech.aop.services;

import com.tech.aop.annotation.TrackExecutionTime;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @TrackExecutionTime
    public void processPayment() {

        try {
            Thread.sleep(500); // simulate work
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Payment processed");
    }

    @TrackExecutionTime
    public void refundPayment() {

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Refund processed");
    }
}
