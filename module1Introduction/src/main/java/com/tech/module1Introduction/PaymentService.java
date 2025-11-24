package com.tech.module1Introduction;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

@Component
@Controller
@Repository
public class PaymentService {

    public void pay(){
        System.out.println("Paying....");
    }
    @PostConstruct
    public void afterInit(){
        System.out.println("Before paying.");
    }
    @PreDestroy
    public void beforeDestroy(){
        System.out.println("After payment is done.");
    }
}
