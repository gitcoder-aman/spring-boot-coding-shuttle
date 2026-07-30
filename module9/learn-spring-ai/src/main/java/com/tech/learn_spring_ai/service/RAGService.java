package com.tech.learn_spring_ai.service;

import com.tech.learn_spring_ai.advisor.TokenUsageAdvisor;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RAGService {
    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final ChatMemory chatMemory;

    @Value("classpath:sql_q.pdf")
    Resource resource;

    public String askAIWithAdvisors(String prompt,String userId){
        return chatClient.prompt()
                .system("""
                You are an AI assistant called Cody.
                Greet users with your Name (Cody) and the user name if you know their name.
                Answer in a friendly, conversational tone.
                """)
                .user(prompt)
                .advisors(
                        //restrict the some content to ask with AI
//                        new SafeGuardAdvisor(List.of(
//                           "Politics",
//                           "Gaming"
//                        )),
                        //short term memory
                        MessageChatMemoryAdvisor.builder(chatMemory)
                                        .conversationId(userId)
                                                .build(),
                        //long term memory
                        VectorStoreChatMemoryAdvisor.builder(vectorStore)
                                .conversationId(userId)
                                .defaultTopK(4)
                                .build(),

                        //when we want to find specific pdf related question/answer then we can do this way
//                        QuestionAnswerAdvisor.builder(vectorStore)
//                                .searchRequest(SearchRequest.builder()
//                                        .filterExpression("file_name == 'sql_q.pdf'")
//                                        .build())
//                                .build(),

                        new TokenUsageAdvisor()
                )
                .call()
                .content();
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
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder()
                .query(prompt)
                .similarityThreshold(0.4)
                .topK(4)
                .filterExpression("file_name=='sql_q.pdf'")
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

    public void ingestPdfToVectorStore(){
        PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
        List<Document>pages = reader.get();

        TokenTextSplitter tokenTextSplitter = TokenTextSplitter.builder()
                .withChunkSize(200)
                .build();

        List<Document>chunks = tokenTextSplitter.apply(pages);
        vectorStore.add(chunks);
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

}
