package com.tech.convertCurrency.Convert.Currency.services.impl;

import com.tech.convertCurrency.Convert.Currency.entities.CurrencyResponse;
import com.tech.convertCurrency.Convert.Currency.entities.HistoricalRateResponse;
import com.tech.convertCurrency.Convert.Currency.entities.LatestRateResponse;
import com.tech.convertCurrency.Convert.Currency.entities.StatusResponse;
import com.tech.convertCurrency.Convert.Currency.client.FreeCurrencyApiClient;
import com.tech.convertCurrency.Convert.Currency.services.FreeCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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

        LatestRateResponse latestRateResponse = freeCurrencyApiClient.convertCurrency(fromCurrency, toCurrency, units);
        Map<String, BigDecimal> updatedRates = new HashMap<>();
        assert latestRateResponse != null;
        latestRateResponse.getData().forEach((currency, value) -> {
            BigDecimal calculateUnits = value.multiply(BigDecimal.valueOf(units));
            updatedRates.put(currency, calculateUnits);
        });
        latestRateResponse.setData(updatedRates);
        return latestRateResponse;
    }

    @Override
    public HistoricalRateResponse getHistoryOfExchangeRate(String date, String baseCurrency, String currencies) {
        return freeCurrencyApiClient.getHistoryOfExchangeRate(date,baseCurrency,currencies);
    }
}
