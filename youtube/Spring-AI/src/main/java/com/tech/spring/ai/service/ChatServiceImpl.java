package com.tech.spring.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    private final ChatClient chatClient;
    public ChatServiceImpl(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    @Override
    public String chat(String query) {

        String prompt = "tell me about virat kohli?";

        //call the llm for response
//        String content = chatClient.prompt()
//                .user(prompt)
//                .system("As a expert in cricket.")
//                .call()
//                .content();



//        var metadata = chatClient
//                .prompt(query)
//                .call()
//                .chatResponse()
//                .getMetadata();

//        var response = chatClient
//                .prompt(query)
//                .call()
//                .chatResponse();
//
//        var metadata = response.getMetadata();
//        var content = response.getResult().getOutput().getText();
//
//        System.out.println(metadata);

//        var response = chatClient
//                .prompt(query)
//                .call()
//                .entity(Tut.class);

//        List<Tut> response = chatClient
//                .prompt(query)
//                .call()
//                .entity(new ParameterizedTypeReference<List<Tut>>() {
//                });

//        Prompt prompt1 = new Prompt(prompt, OllamaChatOptions.builder()
//                .model("llama3.1")
//                .temperature(0.3)
//                .maxTokens(100)
//                .build());
        var response = chatClient
                .prompt(query)
                .call()
                .content();

        return response;
    }
}
