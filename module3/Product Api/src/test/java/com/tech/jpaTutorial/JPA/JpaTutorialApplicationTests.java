package com.tech.jpaTutorial.JPA;

import com.tech.jpaTutorial.JPA.entities.ProductEntity;
import com.tech.jpaTutorial.JPA.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class JpaTutorialApplicationTests {

	@Autowired
	ProductRepository productRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testRepository(){

		ProductEntity productEntity = ProductEntity.builder()
				.sku("nestle2")
				.title("Nestle Chocolate Shake")
				.price(BigDecimal.valueOf(123.45))
				.quantity(12)
				.build();

		ProductEntity saveProductEntity = productRepository.save(productEntity);
		System.out.println(saveProductEntity);
	}

	@Test
	void getRepository(){
//		List<ProductEntity>entities = productRepository.findByCreatedAtAfter(LocalDateTime.of(2024,1,1,0,0,0));

//		List<ProductEntity>entities = productRepository.findByQuantityAndPrice(4,BigDecimal.valueOf(23.45));

//		List<ProductEntity>entities = productRepository.findByTitleLike("%choco%");
		List<ProductEntity>entities = productRepository.findByTitleContainingIgnoreCase("choco");
		System.out.println(entities);
	}

	@Test
	void getSingleFromRepository(){
		Optional<ProductEntity> productEntity = productRepository.findByTitleAndPrice("Nestle Chocolate Shake",BigDecimal.valueOf(123.45));
		System.out.println(productEntity.isPresent());
		productEntity.ifPresent(System.out::println);
		productEntity.ifPresent(entity-> System.out.println(entity));
	}
}
