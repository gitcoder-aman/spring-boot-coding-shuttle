package com.tech.convertCurrency.Convert.Currency.entities;

import lombok.Data;

@Data
public class CurrencyDetailResponse{
	private String symbol;
	private String name;

	private String symbol_native;

	private int decimal_digits;
	private int rounding;

	private String code;
	private String name_plural;
	private String type;
}