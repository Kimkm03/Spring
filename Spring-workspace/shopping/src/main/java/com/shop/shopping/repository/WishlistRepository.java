package com.shop.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.shopping.model.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer>{
	@Query("SELECT w.id FROM Wishlist w WHERE w.productCode = :productCode")
	Integer findwishIdByProductCode(@Param("productCode") Integer productCode);

	@Query("SELECT w.id FROM Wishlist w WHERE w.styleCode = :styleCode")
	Integer findwishIdByStyleCode(@Param("styleCode") Integer styleCode);
	
	@Query("SELECT w FROM Wishlist w WHERE w.productCode != 0 AND w.memberCode = :code")
	List<Wishlist> findByProductCodeIsNotNull(@Param("code") Integer code);
	
	@Query("SELECT w FROM Wishlist w WHERE w.styleCode != 0 AND w.memberCode = :code")
	List<Wishlist> findByStyleCodeIsNotNull(@Param("code") Integer code);
	
	// 관심 목록에 해당 상품이 존재하는지 확인하는 쿼리 (int 타입 사용)
    @Query("SELECT COUNT(w) > 0 FROM Wishlist w WHERE w.styleCode = :styleCode AND w.memberCode = :memberCode")
    boolean existsByStyleCodeAndMemberCode(int styleCode, int memberCode);
}
