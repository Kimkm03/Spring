package com.shop.shopping.service;

import com.shop.shopping.dto.CartItemDTO;
import com.shop.shopping.model.CartItem;
import com.shop.shopping.repository.CartItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    
    public List<CartItemDTO> findCartItemDTOByCartId(int cartId) {
        return cartItemRepository.findCartItemDTOByCartId(cartId);
    }
    
    public List<CartItemDTO> findCartItemDTOById(int id) {
        return cartItemRepository.findCartItemDTOById(id);
    }

    @Transactional
    public int deleteCartItem(Integer id) {
    	try {
    		cartItemRepository.deleteById(id);
            return 1; // Successful deletion
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MemberService : deleteMember() : " + e.getMessage());
            return -1; // Error occurred during deletion
        }    
    }
}