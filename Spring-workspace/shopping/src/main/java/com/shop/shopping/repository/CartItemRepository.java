package com.shop.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.shopping.dto.CartItemDTO;
import com.shop.shopping.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	 @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId")
	 List<CartItem> findByCartId(int cartId);
	 
	 @Query("SELECT new com.shop.shopping.dto.CartItemDTO(ci.id, ci.product, ci.quantity, ci.size, ci.color, ci.count, ci.price) FROM CartItem ci WHERE ci.id = :Id")
	 List<CartItemDTO> findCartItemDTOById(int Id);
	 
	 @Query("SELECT new com.shop.shopping.dto.CartItemDTO(ci.id, ci.product, ci.quantity, ci.size, ci.color, ci.count, ci.price) FROM CartItem ci WHERE ci.cart.id = :cartId")
	 List<CartItemDTO> findCartItemDTOByCartId(int cartId);
}
