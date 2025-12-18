package com.tech.jpaTutorial.JPA.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_table",uniqueConstraints = {
        @UniqueConstraint(name = "sku_unique",columnNames = {"sku"}),
        @UniqueConstraint(name = "title_price_unique",columnNames = {"title","price"})
},indexes = {
        @Index(name = "sku_index",columnList = "sku")
})
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 20)
    private String sku;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
