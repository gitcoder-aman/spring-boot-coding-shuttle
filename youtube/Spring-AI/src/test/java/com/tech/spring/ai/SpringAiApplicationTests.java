package com.tech.spring.ai;

import com.tech.spring.ai.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringAiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private ChatService chatService;

	@Test
	void testTemplateRender(){
		System.out.println("Template Renderer:");
		var output = this.chatService.chatTemplate();
		System.out.println(output);
	}

}
