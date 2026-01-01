package com.tech.convertCurrency.Convert.Currency.entities;

import lombok.Data;

import java.util.Map;

@Data
public class HistoricalRateResponse {

    private Map<String, Map<String, Double>> data;
}
