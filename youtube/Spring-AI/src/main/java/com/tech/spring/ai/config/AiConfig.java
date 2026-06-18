package com.tech.spring.ai.config;

//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.google.genai.GoogleGenAiChatModel;
//import org.springframework.ai.ollama.OllamaChatModel;
//import org.springframework.context.annotation.Bean;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

//    @Bean(name = "genAiChatClient")
//    public ChatClient genAiChatModel(GoogleGenAiChatModel googleGenAiChatModel){
//        return ChatClient.builder(googleGenAiChatModel).build();
//    }
//    @Bean(name = "ollamaChatClient")
//    public ChatClient ollamaChatModel(OllamaChatModel ollamaChatModel){
//        return ChatClient.builder(ollamaChatModel).build();
//    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder){
        return builder
                .defaultSystem("You are a helpful coding assistant. You are an expert in coding.")
                .defaultOptions(OllamaChatOptions.builder()
                        .model("llama3.1")
                        .temperature(0.3)
                        .maxTokens(1000)).build();
    }

}
