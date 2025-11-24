package com.tech.module1Introduction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Module1IntroductionApplication implements CommandLineRunner {

	@Autowired
	PaymentService paymentService1;

	@Autowired
	PaymentService paymentService2;

	public static void main(String[] args) {
		SpringApplication.run(Module1IntroductionApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		// no two objects are created here ,because bean is work on singleton
		System.out.println(paymentService1.hashCode());
		System.out.println(paymentService2.hashCode());
		paymentService1.pay();
		paymentService2.pay();
	}
}
