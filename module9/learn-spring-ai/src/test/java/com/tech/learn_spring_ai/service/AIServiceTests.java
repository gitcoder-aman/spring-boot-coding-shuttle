package com.tech.learn_spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Map;

//@SpringBootTest(properties = {
//        "spring.ai.vectorstore.pgvector.initialize-schema=true",
//        "spring.datasource.url=postgres",
//        "spring.datasource.username=",
//        "spring.datasource.password="
//})
@SpringBootTest
//@Testcontainers
public class AIServiceTests {

    @Autowired
    private AIService aiService;

    // ✅ Use pgvector image (IMPORTANT)

//    @Container
//    static PostgreSQLContainer<?> postgres =
//            new PostgreSQLContainer<>("pgvector/pgvector:pg15")
//                    .withDatabaseName("testdb")
//                    .withUsername("postgres")
//                    .withPassword("postgres");
//
//    @DynamicPropertySource
//    static void overrideProps(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//    }

    @Test
    public void testGetJokes(){
        var joke = aiService.getJoke("Dog");
        System.out.println(joke);
    }

    @Test
    public void testEmbedText(){
        var embed = aiService.getEmbedding("This is a big text here");
        System.out.println(embed.length);

        for (float e : embed) {
            System.out.println(e+" ");
        }
    }
    @Test
    public void testStoreData(){
//        aiService.ingestDataToVectorStore("This is a big text");

    }

    @Test
    public void testSimilaritySearch(){
       var res =  aiService.similaritySearch("Interstellar explores space, time, and world.");
       for (var doc : res)
        System.out.println(doc);
    }
    @Test
    public void testAskAI(){
       var res =  aiService.askAI("What is Apple");
        System.out.println(res);
    }
}
