package com.tech.docker.product_service.service;

import com.tech.docker.product_service.entity.Product;
import com.tech.docker.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(Product product) {
        log.info("Creating product:{}",product);
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        log.info("fetching all products");
        return productRepository.findAll();
    }
}
