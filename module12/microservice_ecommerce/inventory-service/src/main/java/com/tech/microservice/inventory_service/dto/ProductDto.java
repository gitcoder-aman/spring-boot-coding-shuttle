package com.tech.microservice.inventory_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Long id;

    private String title;

    private Double price;

    private Integer stock;
}
