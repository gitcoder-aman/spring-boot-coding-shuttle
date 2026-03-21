package com.tech.learn_spring_ai.service;

import com.tech.learn_spring_ai.dto.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AIService {
    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public float[] getEmbedding(String text){
        return embeddingModel.embed(text);
    }
    public void ingestDataToVectorStore(String text){
//        Document document = new Document(text);
//        vectorStore.add(List.of(document));
        List<Document> movies = List.of(

                new Document("Inception is a sci-fi movie about dreams within dreams.",
                        Map.of("title", "Inception", "year", "2010")),

                new Document("Interstellar explores space, time, and black holes.",
                        Map.of("title", "Interstellar", "year", "2014")),

                new Document("The Dark Knight is a superhero movie featuring Batman and Joker.",
                        Map.of("title", "The Dark Knight", "year", "2008")),

                new Document("Avengers Endgame is a Marvel movie about saving the universe.",
                        Map.of("title", "Avengers Endgame", "year", "2019"))
        );
        vectorStore.add(movies);
        vectorStore.add(springAiDocs());
    }

    private List<Document> springAiDocs() {

        return List.of(

                new Document("Spring AI is a framework that simplifies integration of AI models like OpenAI into Spring Boot applications.",
                        Map.of("topic", "Spring AI", "type", "Introduction")),

                new Document("Embedding models convert text into numerical vectors which are used for semantic search and similarity matching.",
                        Map.of("topic", "Embeddings", "type", "Concept")),

                new Document("Vector stores are databases designed to store embeddings and perform similarity search using mathematical distance metrics.",
                        Map.of("topic", "Vector Store", "type", "Database")),

                new Document("Retrieval Augmented Generation (RAG) combines vector search with LLMs to provide context-aware and accurate responses.",
                        Map.of("topic", "RAG", "type", "Architecture"))
        );
    }

    public List<Document> similaritySearch(String text){
        return vectorStore.similaritySearch(SearchRequest.builder()
                        .query(text)
                        .topK(5)
                .build());
    }
    public String askAI(String prompt){

        String template = """
                You are an AI assistant helping a developer.
                
                Rules:
                - Use only the information provide in the context
                - You may rephrase , summarize, and explain in natural language
                - Do not introduce new concept or facts
                - If multiple context sections are relevant, combine them into a single explanation
                - If the answer is not present , say "I don't know"
                
                Context:
                {context}
                
                Answer in a friendly , conversational tone.
                """;
        List<Document>documents = vectorStore.similaritySearch(SearchRequest.builder()
                        .query(prompt)
                        .similarityThreshold(0.5)
                        .topK(2)
                        .filterExpression("topic == 'Spring AI' or topic == 'Vector Store'")
                .build());
        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        PromptTemplate promptTemplate = new PromptTemplate(template);
         String systemPrompt= promptTemplate.render(Map.of("context",context));

        return chatClient
                .prompt()
                .system(systemPrompt)
                .user(prompt)
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .content();
    }
    public String getJoke(String topic){

        String systemPrompt = """
                You are a sarcastic joker,you make poetic jokes in 4 lines.
                You don't make jokes about politics.
                Give a joke on the topic: {topic}
                """;
        PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);
        String renderedText = promptTemplate.render(Map.of("topic",topic));

        var response = chatClient.prompt()
                .user(renderedText)
                .advisors(new SimpleLoggerAdvisor())
                .call()
//                .chatClientResponse();
//                .entity(Joke.class);
                .content();

//        return chatClient.prompt()
//                .user("Give me a joke of : "+topic)
//                .call()
//                .content();
//        assert response.chatResponse() != null;
//        assert response != null;
        return response;

    }
}
