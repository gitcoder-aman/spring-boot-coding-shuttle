package com.tech.microservice.inventory_service.config;

import org.hibernate.annotations.Bag;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return  new ModelMapper();
    }

    @Bean
    public RestClient restClient(){
        return RestClient.builder().build();
    }
}
