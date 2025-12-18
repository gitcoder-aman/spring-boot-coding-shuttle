package com.tech.jpaTutorial.JPA.services;

import com.tech.jpaTutorial.JPA.dto.ProductDTO;
import com.tech.jpaTutorial.JPA.entities.ProductEntity;
import com.tech.jpaTutorial.JPA.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {


    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private  ModelMapper modelMapper;

//    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
//        this.productRepository = productRepository;
//        this.modelMapper = modelMapper;
//    }

    public List<ProductDTO>getAllProductsWhoseFindTitleAndPrice(String title, BigDecimal price){
        Optional<ProductEntity> productEntityList = productRepository.findByTitleAndPrice(title,price);
        return productEntityList
                .stream()
                .map(productEntity-> modelMapper.map(productEntity, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProductDTO>findByOrderByPrice(){
        List<ProductEntity>productEntities = productRepository.findByOrderByPrice();

        return productEntities
                .stream()
                .map(productEntity-> modelMapper.map(productEntity, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProductDTO>findBy(String sortBy){
//        List<ProductEntity> productEntities = productRepository.findBy(Sort.by(Sort.Direction.ASC,sortBy,"price"));
        List<ProductEntity> productEntities = productRepository.findBy(Sort.by(Sort.Order.asc(sortBy)));
        return productEntities
                .stream()
                .map(productEntity-> modelMapper.map(productEntity, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public Page<ProductDTO> findAllProducts(Pageable pageable) {
        Page<ProductEntity> productEntities = productRepository.findAll(pageable);

        return productEntities.map(productEntity -> modelMapper.map(productEntity, ProductDTO.class));

    }
}
