package com.tech.docker.product_service.repository;

import com.tech.docker.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
