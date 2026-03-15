package com.tech.learn_spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AIServiceTests {

    @Autowired
    private AIService aiService;

    @Test
    public void testGetJokes(){
        var joke = aiService.getJoke("Dog");
        System.out.println(joke);
    }
}
