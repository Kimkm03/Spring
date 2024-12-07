package com.shop.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.shopping.model.Style;

@Repository
public interface StyleRepository extends JpaRepository<Style, Integer>{
	@Query(value = "SELECT * FROM Style WHERE memnum = :memnum", nativeQuery = true)
	List<Style> findByMemNum(@Param("memnum") Integer memnum);
	
	// 여러 필터 조건에 맞는 스타일 데이터를 조회하는 메서드
    @Query("SELECT s FROM Style s WHERE " +
           "(COALESCE(:gender, s.gender) = s.gender) AND " +
           "(COALESCE(:season, s.season) = s.season) AND " +
           "(COALESCE(:tpo, s.tpo) = s.tpo) AND " +
           "(COALESCE(:uppercolor, s.uppercolor) = s.uppercolor)")
    List<Style> findStylesByFilters(String gender, String season, String tpo, String uppercolor);
}
