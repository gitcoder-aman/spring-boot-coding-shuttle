package com.tech.convertCurrency.Convert.Currency.client;

import com.tech.convertCurrency.Convert.Currency.entities.CurrencyResponse;
import com.tech.convertCurrency.Convert.Currency.entities.LatestRateResponse;
import com.tech.convertCurrency.Convert.Currency.entities.StatusResponse;
import com.tech.convertCurrency.Convert.Currency.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
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
                        throw new ResourceNotFoundException("Invalid API Key or Bad Request");
                    }).onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new RuntimeException("FreeCurrency API server error");
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
                        throw new ResourceNotFoundException("Invalid API Key or Bad Request");
                    }).onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new RuntimeException("FreeCurrency API server error");
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
            LatestRateResponse body = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/latest")
                            .queryParam("apikey", api_key)
                            .queryParam("base_currency", fromCurrency)
                            .queryParam("currencies", toCurrency)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("Invalid API Key or Bad Request");
                    }).onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new RuntimeException("FreeCurrency API server error");
                    })
                    .body(new ParameterizedTypeReference<LatestRateResponse>() {
                    });
            Map<String, BigDecimal> updatedRates = new HashMap<>();
            assert body != null;
            body.getData().forEach((currency, value) -> {
                BigDecimal calculateUnits = value.multiply(BigDecimal.valueOf(units));
                updatedRates.put(currency, calculateUnits);
            });
            body.setData(updatedRates);
            return body;
        }catch (Exception e){
            log.error("Exception occurred in convertCurrency",e);
            throw new RuntimeException(e);
        }
    }
}
