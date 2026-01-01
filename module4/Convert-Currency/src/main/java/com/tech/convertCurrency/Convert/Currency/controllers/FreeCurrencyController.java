package com.tech.convertCurrency.Convert.Currency.controllers;

import com.tech.convertCurrency.Convert.Currency.entities.*;
import com.tech.convertCurrency.Convert.Currency.services.FreeCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//https://api.freecurrencyapi.com/v1/status?apikey=fca_live_M8d13j8aC0RlQGloFxxpFX9cWAFy33tyBwbwud94

//https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_M8d13j8aC0RlQGloFxxpFX9cWAFy33tyBwbwud94&currencies=EUR%2CUSD%2CCAD
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/currency")
public class FreeCurrencyController {

    private final FreeCurrencyService freeCurrencyService;

    @GetMapping("/status")
    ResponseEntity<StatusResponse>getStatusResponse(){
        StatusResponse statusResponse = freeCurrencyService.getStatus();
        return ResponseEntity.ok(statusResponse);
    }
    @GetMapping("/detail")
    ResponseEntity<CurrencyResponse>getCurrencyDetailResponse(@RequestParam("currencyName") String currencyName){
        CurrencyResponse currencyResponse = freeCurrencyService.getCurrencyDetail(currencyName);
        return ResponseEntity.ok(currencyResponse);
    }

    @GetMapping("/convertCurrency")
    ResponseEntity<LatestRateResponse>getConvertCurrencyResponse(@RequestParam("fromCurrency") String fromCurrency,
                                                                      @RequestParam("toCurrency") String toCurrency,
                                                                 @RequestParam("units") Integer units){
        log.info("inside controller toCurrency list{}",toCurrency);
        LatestRateResponse latestRateResponse = freeCurrencyService.convertCurrency(fromCurrency, toCurrency,units);
        return ResponseEntity.ok(latestRateResponse);
    }

    @GetMapping("/history")
    ResponseEntity<HistoricalRateResponse>getHistoryOfExchangeRate(@RequestParam(value = "date") String date ,
                                                                   @RequestParam(value = "base_currency",required = false) String baseCurrency,
                                                                   @RequestParam(value = "currencies",required = false) String currencies){
        HistoricalRateResponse historicalRateResponse = freeCurrencyService.getHistoryOfExchangeRate(date,baseCurrency,currencies);
        return ResponseEntity.ok(historicalRateResponse);
    }
}
