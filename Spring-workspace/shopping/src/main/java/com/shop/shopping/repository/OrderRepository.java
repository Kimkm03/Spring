package com.shop.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.shopping.model.Order;
import com.shop.shopping.model.Product;

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

	@Query(value = """
		    SELECT * FROM orders 
		    WHERE userCode = :userCode
		      AND (
		        :orderStatus IS NULL AND  
		        orderStatus IN ('PREPARING_SHIPMENT', 'IN_TRANSIT', 'DELIVERED') OR 
		        orderStatus = :orderStatus
		      )
		      AND (:startDate IS NULL OR orderDate >= :startDate) 
		      AND (:endDate IS NULL OR orderDate <= :endDate)
		""", nativeQuery = true)
		List<Order> findOrdersByCriteria(
		    @Param("userCode") Integer userCode,
		    @Param("orderStatus") String orderStatus,
		    @Param("startDate") String startDate,
		    @Param("endDate") String endDate
		);

	@Query(value = "SELECT * FROM orders o JOIN product p ON o.productcode = p.pnum WHERE p.pname LIKE %:name% AND o.userCode = :code", nativeQuery = true)
	List<Order> findByProductNameContaining(
	        @Param("name") String name,
	        @Param("code") Integer code);


}
