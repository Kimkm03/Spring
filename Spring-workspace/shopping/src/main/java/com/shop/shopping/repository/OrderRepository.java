package com.shop.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.shopping.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	// userCode를 기준으로 주문을 조회하는 메소드
	@Query(value = "SELECT * FROM orders WHERE usercode = :userCode", nativeQuery = true)
	List<Order> findByUserCode(@Param("userCode") Integer userCode);
	
	@Query(value = "SELECT * FROM orders WHERE usercode = :userCode AND orderStatus IN ('PREPARING_SHIPMENT', 'IN_TRANSIT', 'DELIVERED')", nativeQuery = true)
	List<Order> findByUserCodeAndOrderStatus(@Param("userCode") Integer userCode);
	
	@Query(value = "SELECT COUNT(*) FROM orders WHERE DATE(OrderDate) = CURDATE()", nativeQuery = true)
	Integer countOrderDateToday();

	@Query(value = "SELECT COUNT(*) FROM orders", nativeQuery = true)
	Integer getTotalOrdersCount();
}
