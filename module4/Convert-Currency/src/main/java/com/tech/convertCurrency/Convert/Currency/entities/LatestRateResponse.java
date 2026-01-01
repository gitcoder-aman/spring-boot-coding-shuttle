package com.tech.convertCurrency.Convert.Currency.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class LatestRateResponse {
    private Map<String, BigDecimal> data;
}

