package com.tech.convertCurrency.Convert.Currency.services;


import com.tech.convertCurrency.Convert.Currency.entities.CurrencyResponse;
import com.tech.convertCurrency.Convert.Currency.entities.LatestRateResponse;
import com.tech.convertCurrency.Convert.Currency.entities.StatusResponse;

public interface FreeCurrencyService {

     StatusResponse getStatus();

    CurrencyResponse getCurrencyDetail(String currencyName);

    LatestRateResponse convertCurrency(String fromCurrency, String toCurrency,Integer units);
}
