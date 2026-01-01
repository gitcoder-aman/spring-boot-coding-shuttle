package com.tech.convertCurrency.Convert.Currency.entities;

import lombok.Data;

import java.util.Map;

@Data
public class CurrencyResponse {
    private Map<String, CurrencyDetailResponse> data;
}

