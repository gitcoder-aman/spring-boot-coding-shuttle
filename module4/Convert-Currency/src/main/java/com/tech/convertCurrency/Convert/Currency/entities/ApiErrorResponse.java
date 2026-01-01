package com.tech.convertCurrency.Convert.Currency.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
public class ApiErrorResponse {

    private String message;
    private Map<String, List<String>> errors;
    private String info;
}
