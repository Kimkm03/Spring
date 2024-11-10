package com.shop.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.shopping.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
	@Query(value = "SELECT * FROM board WHERE usercode = :userCode", nativeQuery = true)
	List<Board> findByUserCode(@Param("userCode") Integer userCode);
}
