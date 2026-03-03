package com.tech.deployment.deploy;

import com.tech.deployment.deploy.services.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class DeployApplication implements CommandLineRunner {

	private final DataService dataService;

	@Value("${my.variable}")
	private String myVariable;

	public static void main(String[] args) {
		SpringApplication.run(DeployApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("my variable: "+myVariable);
		System.out.println("The data is:"+dataService.getData());
	}
}
