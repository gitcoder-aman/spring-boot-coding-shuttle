package com.tech.convertCurrency.Convert.Currency.services.impl;

import com.tech.convertCurrency.Convert.Currency.entities.CurrencyResponse;
import com.tech.convertCurrency.Convert.Currency.entities.LatestRateResponse;
import com.tech.convertCurrency.Convert.Currency.entities.StatusResponse;
import com.tech.convertCurrency.Convert.Currency.client.FreeCurrencyApiClient;
import com.tech.convertCurrency.Convert.Currency.services.FreeCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreeCurrencyServiceImpl implements FreeCurrencyService {

    private final FreeCurrencyApiClient freeCurrencyApiClient;

    public StatusResponse getStatus(){
        return freeCurrencyApiClient.getStatus();
    }

    @Override
    public CurrencyResponse getCurrencyDetail(String currencyName) {
        return freeCurrencyApiClient.getCurrencyDetails(currencyName);
    }

    @Override
    public LatestRateResponse convertCurrency(String fromCurrency, String toCurrency, Integer units) {
        return freeCurrencyApiClient.convertCurrency(fromCurrency,toCurrency,units);
    }
}
