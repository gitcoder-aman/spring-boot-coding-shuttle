package com.tech.docker.product_service.controller;

import com.tech.docker.product_service.entity.Product;
import com.tech.docker.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello From Spring Boot";
    }

    @PostMapping
    public String createProduct(@RequestBody Product product){
        productService.createProduct(product);
        return "Product created successfully";
    }
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
}
