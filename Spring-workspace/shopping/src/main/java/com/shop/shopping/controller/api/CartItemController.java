package com.shop.shopping.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shopping.dto.CartItemDTO;
import com.shop.shopping.dto.ResponseDto;
import com.shop.shopping.model.CartItem;
import com.shop.shopping.service.CartItemService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/cart/item")
@CrossOrigin(origins = "http://localhost:3000")
public class CartItemController {
	private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItemDTO>> getCartItemsByCartId(@PathVariable int cartId) {
        try {
            List<CartItemDTO> cartItems = cartItemService.findCartItemDTOByCartId(cartId);
            return ResponseEntity.ok(cartItems); // 성공 시 카트 아이템 목록 반환
        } catch (Exception e) {
            e.printStackTrace(); // 예외 로그 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>()); // 실패 시 빈 목록 반환
        }
    }
    
    @GetMapping("/selectItems/{id}")
    public ResponseEntity<List<CartItemDTO>> getSelectCartItem(@PathVariable int id){
    	try {
    		List<CartItemDTO> cartItems = cartItemService.findCartItemDTOById(id);
            return ResponseEntity.ok(cartItems); // 성공 시 카트 아이템 목록 반환
    	} catch (Exception e) {
    		e.printStackTrace(); // 예외 로그 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>()); // 실패 시 빈 목록 반환
		}
    }
    
    @PostMapping("/deleteCartItem/{id}")
    public ResponseDto<Integer> deleteCartItem(@PathVariable Integer id) {
    	int result = cartItemService.deleteCartItem(id);
        if (result == 1) {
            return new ResponseDto<>(HttpStatus.OK.value(), result); // 1 indicates successful deletion
        } else {
            return new ResponseDto<>(HttpStatus.NOT_FOUND.value(), -1); // -1 indicates failure due to member not found
        }
    }
}
