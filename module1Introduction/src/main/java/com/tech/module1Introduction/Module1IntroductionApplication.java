package com.tech.module1Introduction;

import com.tech.module1Introduction.impl.EmailNotificationService;
import com.tech.module1Introduction.impl.SmsNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Module1IntroductionApplication implements CommandLineRunner {

	@Autowired
	PaymentService paymentService1;

	@Autowired
	PaymentService paymentService2;

//    @Autowired
    final NotificationService notificationService;

    public Module1IntroductionApplication(@Qualifier("emailNotif") NotificationService notificationService) {
        this.notificationService = notificationService;  // constructor DI preferred
    }

    @Autowired
    Map<String,NotificationService> notificationServiceMap = new HashMap<>();

    public static void main(String[] args) {
		SpringApplication.run(Module1IntroductionApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		// no two objects are created here ,because bean is work on singleton
//		System.out.println(paymentService1.hashCode());
//		System.out.println(paymentService2.hashCode());
//		paymentService1.pay();
//		paymentService2.pay();

//        notificationService.send("Hello");
//        notificationService = null;  // we can't able to change while working with constructor

        for (var notificationService : notificationServiceMap.entrySet()){
            System.out.println(notificationService.getKey());
            notificationService.getValue().send("Map Hello");
        }
	}
}
