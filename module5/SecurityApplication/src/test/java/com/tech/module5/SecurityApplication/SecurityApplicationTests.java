package com.tech.module5.SecurityApplication;

import com.tech.module5.SecurityApplication.entities.UserApp;
import com.tech.module5.SecurityApplication.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {

		UserApp userApp = new UserApp(4L,"aman@gmail.com","11234","Aman Kumar");
		String token = jwtService.generateAccessToken(userApp);

		System.out.println(token);

		Long id = jwtService.getUserIdFromToken(token);
		System.out.println(id);
	}

}
