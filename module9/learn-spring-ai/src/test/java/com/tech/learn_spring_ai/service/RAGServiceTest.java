package com.tech.learn_spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RAGServiceTest {

    @Autowired
    private RAGService ragService;

    @Test
    public void testIngest(){
//        ragService.ingestPdfToVectorStore();
    }
    @Test
    public void testAskAI(){
        var response = ragService.askAI("What is LIMIT Clause?");
        System.out.println(response);
    }
    @Test
    public void testAskAIWithAdvisor(){
//        var response = ragService.askAIWithAdvisors("Can you tell me capital of India and also my name is Aman.","aman123");
//        var response = ragService.askAIWithAdvisors("What is my name?","rohit123");
//        var response = ragService.askAIWithAdvisors("Well my name is Rohit Khanna and dob is 20 Aug 2004,can you tell me the way to Newyork?","rohit123");
//        var response = ragService.askAIWithAdvisors("Going from Delhi,whats the best way to get there","rohit123");
//        var response = ragService.askAIWithAdvisors("How will I come back then?also what do you know about me?","rohit123");
//        var response = ragService.askAIWithAdvisors("okay,what is the weather like in Delhi?","rohit123");
//        var response = ragService.askAIWithAdvisors("okay tell me , can we go delhi in this time?","rohit123");
        var response = ragService.askAIWithAdvisors("actually you had to store in long term memory,please check it ,what is my name and dob?","rohit123");
        System.out.println(response);
    }
}
