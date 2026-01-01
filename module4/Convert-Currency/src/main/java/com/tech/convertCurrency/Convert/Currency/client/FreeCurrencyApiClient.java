package com.tech.convertCurrency.Convert.Currency.client;

import com.tech.convertCurrency.Convert.Currency.entities.*;
import com.tech.convertCurrency.Convert.Currency.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


//https://api.freecurrencyapi.com/v1/status?apikey=fca_live_M8d13j8aC0RlQGloFxxpFX9cWAFy33tyBwbwud94
//https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_M8d13j8aC0RlQGloFxxpFX9cWAFy33tyBwbwud94&currencies=EUR%2CUSD%2CCAD

//https://api.freecurrencyapi.com/v1/currencies?apikey=fca_live_M8d13j8aC0RlQGloFxxpFX9cWAFy33tyBwbwud94&currencies=EUR%2CINR
//https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_M8d13j8aC0RlQGloFxxpFX9cWAFy33tyBwbwud94&currencies=USD%2CINR

//https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_M8d13j8aC0RlQGloFxxpFX9cWAFy33tyBwbwud94&currencies=INR
@Component
@RequiredArgsConstructor
@Slf4j
public class FreeCurrencyApiClient {

    private final RestClient restClient;
    @Value("${freeCurrency.api.key}")
    private String api_key;

    public StatusResponse getStatus(){

        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/status")
                            .queryParam("apikey",api_key)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException(new String(res.getBody().readAllBytes()));
                    }).onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new RuntimeException(new String(res.getBody().readAllBytes()));
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (Exception e) {
            log.error("Exception occurred in GetStatus",e);
            throw new RuntimeException(e);
        }
    }

    public CurrencyResponse getCurrencyDetails(String currencyName) {
        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/currencies")
                            .queryParam("apikey",api_key)
                            .queryParam("currencies",currencyName)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException(new String(res.getBody().readAllBytes()));
                    }).onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new RuntimeException(new String(res.getBody().readAllBytes()));
                    })
                    .body(new ParameterizedTypeReference<CurrencyResponse>() {
                    });
        }catch (Exception e){
            log.error("Exception occurred in getCurrencyDetails",e);
            throw new RuntimeException(e);
        }
    }

    public LatestRateResponse convertCurrency(String fromCurrency, String toCurrency, Integer units) {

        log.info("api Client {} {}",fromCurrency,toCurrency);  //USD INR%2CEUR%2CJPY
        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/latest")
                            .queryParam("apikey", api_key)
                            .queryParam("base_currency", fromCurrency)
                            .queryParam("currencies", toCurrency)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
//                        log.error(new String(res.getBody().readAllBytes()));
//                        throw new ResourceNotFoundException(new String(res.getBody().readAllBytes(), StandardCharsets.UTF_8));
                        String errorBody;
                        try {
                            errorBody = new String(res.getBody().readAllBytes(),StandardCharsets.UTF_8);
                        } catch (IOException e) {
                            throw new RuntimeException("Error reading response body", e);
                        }

                        log.error("FreeCurrency API error: {}", errorBody);

                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            ApiErrorResponse apiError = objectMapper.readValue(errorBody, ApiErrorResponse.class);

                            String message = apiError.getErrors()
                                    .values()
                                    .stream()
                                    .findFirst()
                                    .map(list -> list.get(0))
                                    .orElse("Invalid request");

                            throw new ResourceNotFoundException(message+apiError.getMessage());

                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse API error response", e);
                        }
                    }).onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new RuntimeException(new String(res.getBody().readAllBytes()));
                    })
                    .body(new ParameterizedTypeReference<LatestRateResponse>() {
                    });
        }catch (Exception e){
            log.error("Exception occurred in convertCurrency",e);
            throw new RuntimeException(e);
        }
    }

    public HistoricalRateResponse getHistoryOfExchangeRate(String date, String baseCurrency, String currencies) {
        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/historical")
                            .queryParam("apikey", api_key)
                            .queryParam("base_currency", baseCurrency)
                            .queryParam("currencies", currencies)
                            .queryParam("date",date)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
//                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException(new String(res.getBody().readAllBytes(),StandardCharsets.UTF_8));
                    }).onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new RuntimeException(new String(res.getBody().readAllBytes()));
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
        }catch (Exception e){
            log.error("Exception occurred in HistoryOfExchangeRate",e);
            throw new RuntimeException(e);
        }
    }
}
