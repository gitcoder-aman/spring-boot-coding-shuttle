package com.tech.spring.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

//    private final ChatClient chatClient;
    private final ChatClient googleGenAiChatClient;
    private final ChatClient ollamaChatClient;

    @Value("classpath:/prompts/user-message.st")
    private Resource userMessage;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemMessage;

    public ChatServiceImpl(@Qualifier("genAiChatClient") ChatClient googleGenAiChatClient,@Qualifier("ollamaChatClient") ChatClient ollamaChatClient){
        this.googleGenAiChatClient = googleGenAiChatClient;
        this.ollamaChatClient = ollamaChatClient;
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

        //modify this prompt and extra things to prompt make it more interactive.
        String queryStr = "As an expert in coding and programming. Always write program in java. Now reply for this question:{query}";


        var response = googleGenAiChatClient
                .prompt()
                .user(u->u.text(queryStr).param("query",query))
                .call()
                .content();

        return response;
    }

    @Override
    public String chatTemplate(String q){

//        PromptTemplate strTemplate = PromptTemplate.builder().template("What is {techName}? tell me example of {techExample}?").build();
//
//        //render the template;
//        String renderTemplate = strTemplate.render(Map.of(
//                "techName","Spring",
//                "techExample","Spring Exception"
//        ));
//        Prompt prompt = new Prompt(renderTemplate);

        //system prompt template
//        var systemPromptTemplate = SystemPromptTemplate.builder()
//                .template("You are a helpful coding assistant. You are an expert in coding.")
//                .build();
//        var systemMessage = systemPromptTemplate.createMessage();
//        var userPromptTemplate = PromptTemplate.builder().template("What is {techName}? tell me about {techExample}?").build();
//        var userMessage = userPromptTemplate.createMessage(Map.of(
//                "techName","Spring",
//                "techExample","Spring Exception"
//        ));
//        Prompt prompt = new Prompt(systemMessage,userMessage);

        return this.googleGenAiChatClient
                .prompt()
//                .advisors(new SimpleLoggerAdvisor())
                .system(system->
                        system.text(this.systemMessage))
                .user(user->
//                        user.text("What is {techName}? tell me also about {techExample}")
//                                .param("techExample","Spring Controller example")
//                                .param("techName","Collection framework in java"))
                        user.text(this.userMessage).param("concept",q))
                .call()
                .content();
    }
}
