package com.shop.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.shopping.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	@Query(value = "SELECT * FROM review WHERE productcode = :productCode", nativeQuery = true)
	List<Review> findByProductCode(@Param("productCode") Integer productCode);
	
	@Query(value = "SELECT * FROM review WHERE memnum = :memnum", nativeQuery = true)
	List<Review> findByMemnum(@Param("memnum") Integer memnum);
	
	// productCode와 memnum에 해당하는 리뷰를 찾는 메서드
    Review findByProductCodeAndMemnum(Integer productCode, Integer memnum);
}
