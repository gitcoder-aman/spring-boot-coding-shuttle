package com.tech.jpaTutorial.JPA.controllers;

import com.tech.jpaTutorial.JPA.dto.ProductDTO;
import com.tech.jpaTutorial.JPA.entities.ProductEntity;
import com.tech.jpaTutorial.JPA.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {


    private final int PAGE_SIZE = 5;
    @Autowired
    private ProductService productService;

    @GetMapping("/titlePrice")
    public List<ProductDTO>getAllProductsFindTitleAndPrice(){
        return productService.getAllProductsWhoseFindTitleAndPrice("Wireless Mouse", BigDecimal.valueOf(499.99));
    }

    @GetMapping("/orderByPrice")
    public List<ProductDTO> getByOrderByPrice(){
        return productService.findByOrderByPrice();
    }

    @GetMapping("/sort")
    public List<ProductDTO>getAllProducts(
            @RequestParam(defaultValue = "id")String sortBy
    ){
        return productService.findBy(sortBy);
    }

    @GetMapping("/pageable")
    public Page<ProductDTO> getAllProducts(
            @RequestParam(defaultValue = "id")String sortBy,
            @RequestParam(defaultValue = "0") Integer pageNumber
    ){
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(sortBy));
        return productService.findAllProducts(pageable);
    }


}
