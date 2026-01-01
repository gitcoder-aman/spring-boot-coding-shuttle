package com.tech.convertCurrency.Convert.Currency.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import java.net.http.HttpClient;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class RestClientConfig {

    @Value("${freeCurrency.base.url}")
    private String BASE_URL;

    @Bean
    @Qualifier("convertCurrencyRestClient")
    RestClient getConvertCurrencyServiceRestClient(){
        return RestClient
                .builder()
                .baseUrl(BASE_URL)
                .requestFactory(
                        new JdkClientHttpRequestFactory(
                                HttpClient.newBuilder()
                                        .version(HttpClient.Version.HTTP_1_1).build()
                        )
                )
                .defaultHeader(CONTENT_TYPE,APPLICATION_JSON_VALUE)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError,(req, res)->{
                    throw new RuntimeException("Server Error Occurred");
                })
                .build();
    }

}
