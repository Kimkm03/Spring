package com.shop.shopping.repository;

import com.shop.shopping.model.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> { 
	List<Product> findByCategorynum(int categorynum);
	
	@Query("SELECT p FROM Product p WHERE p.views >= 50")
    List<Product> findProductsByViewsGreaterThanEqual();
	
	@Query("SELECT p FROM Product p WHERE p.pcreateDate >= :startDate")
    List<Product> findProductsAddedInLast7Days(@Param("startDate") LocalDateTime startDate);
	
	@Query("SELECT p FROM Product p WHERE p.pname LIKE %:name%")
	List<Product> findByNameContaining(@Param("name") String name);
}
