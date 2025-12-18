package com.tech.jpaTutorial.JPA.repositories;

import com.tech.jpaTutorial.JPA.entities.ProductEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {
    List<ProductEntity> findByTitle(String title);

    List<ProductEntity> findByCreatedAtAfter(LocalDateTime after);

    List<ProductEntity> findByQuantityAndPrice(int quantity, BigDecimal price);

    List<ProductEntity> findByTitleLike(String title);

    List<ProductEntity> findByTitleContainingIgnoreCase(String title);

//    Optional<ProductEntity> findByTitleAndPrice(String title,BigDecimal price);

    @Query("select p from ProductEntity p where p.title=?1 and p.price=?2")
    Optional<ProductEntity> findByTitleAndPrice(String title,BigDecimal price);

    List<ProductEntity> findByOrderByPrice();

    List<ProductEntity> findBy(Sort sort);
}
