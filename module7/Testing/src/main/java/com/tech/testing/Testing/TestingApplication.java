package com.tech.testing.Testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class TestingApplication {

	public static void main(String[] args) {
		System.out.println(TimeZone.getDefault());
		SpringApplication.run(TestingApplication.class, args);
	}

}
