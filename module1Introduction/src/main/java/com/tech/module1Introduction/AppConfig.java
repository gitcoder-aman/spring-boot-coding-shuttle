package com.tech.module1Introduction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
//    @Scope("prototype")  // for new instance every time
    PaymentService paymentService(){

        //more logic before create object
        return new PaymentService();
    }
}
