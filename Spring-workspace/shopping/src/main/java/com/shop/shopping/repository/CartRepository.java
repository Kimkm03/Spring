package com.shop.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.shopping.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
	@Query("SELECT c.cartId FROM Cart c WHERE c.member.memnum = :memnum")
    Integer findCartIdByMemberId(Integer memnum);
}
