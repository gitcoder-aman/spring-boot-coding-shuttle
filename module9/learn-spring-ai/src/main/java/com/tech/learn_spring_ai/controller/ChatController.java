package com.tech.learn_spring_ai.controller;

import com.tech.learn_spring_ai.tool.FlightBookingTool;
import com.tech.learn_spring_ai.tool.TravellingTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatClient chatClient;
    private final TravellingTools travellingTools;
    private final FlightBookingTool flightBookingTool;
    private final ChatMemory chatMemory;

    @PostMapping("/chat")
    public String chat(@RequestBody String message, @RequestParam String userId) {

        String systemPrompt = String.format("""
                You are a flight booking assistant.
                
                CRITICAL RULES:
                
                1. NEVER generate fake data.
                
                2. ONLY use tools to perform actions.
                
                3. DO NOT invent:
                   - flight numbers
                   - airlines
                   - weather
                   - airports
                   - booking details
                
                4. If required information is missing:
                   ASK the user for the missing details.
                
                5. Only confirm a booking AFTER the booking tool successfully runs.
                
                6. If no booking exists:
                   respond exactly:
                   "No booking found."
                
                --------------------------------------------------
                
                CONDITIONAL WORKFLOW RULES:
                
                If the user includes a condition such as:
                
                - if weather is sunny
                - only if temperature is below a value
                - check weather before booking
                
                You MUST:
                
                Step 1: Call the weather tool first
                Step 2: Evaluate the condition
                Step 3: Only create the booking if the condition is satisfied
                Step 4: If the condition is NOT satisfied, inform the user and DO NOT create the booking
                
                Never skip the condition check.
                
                --------------------------------------------------
                
                TOOL USAGE RULES:
                
                Use:
                
                - get_weather → when weather information is required
                - flight_booking_tool → to create bookings
                - get_user_bookings → to retrieve bookings
                - update_booking_status → to cancel or update bookings
                
                --------------------------------------------------
                
                IMPORTANT:
                
                The current user's id is "%s".
                
                Always use this exact value when calling tools.
                """, userId);
        return chatClient.prompt()
                .system(systemPrompt)
                .user(message)
                .tools(travellingTools, flightBookingTool)
                .advisors(MessageChatMemoryAdvisor.builder(chatMemory)
                        .conversationId(userId)
                        .build())
                .call()
                .content();
    }

}
