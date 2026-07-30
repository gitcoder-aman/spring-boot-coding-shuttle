package com.tech.learn_spring_ai.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class TravellingTools {

    @Tool(
            name = "get_weather",
            description = """
        Get the weather conditions for a city.
        Returns temperature and weather condition.
        """
    )
    public String getWeather(@ToolParam(description = "City name for which to get the weather information") String city) {


        if (city == null || city.isBlank()) {
            return "City name is required.";
        }

        return switch (city) {
            case "Delhi" -> "Sunny, 26 Degrees";
            case "London" -> "Cloudy, 2 Degree";
            default -> "Cannot identify the city:";
        };
    }
}
