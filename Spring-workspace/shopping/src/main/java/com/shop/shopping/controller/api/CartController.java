package com.shop.shopping.controller.api;

import com.shop.shopping.dto.ResponseDto;
import com.shop.shopping.model.CartItem;
import com.shop.shopping.service.CartService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/{memberId}/{productId}/add")
	public ResponseDto<Integer> addToCart(@PathVariable int memberId, @PathVariable int productId,
			@RequestBody CartItem cartItem) {
		int result = cartService.addCartItem(memberId, productId, cartItem);
		if (result == 1) {
			return new ResponseDto<>(HttpStatus.OK.value(), result);
		} else {
			return new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), result);
		}
	}

	@GetMapping("/{memberId}/count")
	public ResponseEntity<Integer> getCartItemCount(@PathVariable String memberId) {
		int itemCount = cartService.getCartItemCount(memberId);
		if (itemCount >= 0) {
			return ResponseEntity.ok(itemCount); // 성공 시 상품 수 반환
		} else {
			return ResponseEntity.status(500).body(itemCount); // 실패 시 오류 코드와 함께 반환
		}
	}
	
	@GetMapping("/{memberId}/cartid")
	public ResponseEntity<Integer> getCartItemCartId(@PathVariable Integer memberId) {
	    try {
	        int cartId = cartService.getCartItemId(memberId);
	        return ResponseEntity.ok(cartId); // 성공 시 카트 번호 반환
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1); // 실패 시 오류 코드와 함께 반환
	    }
	}
	
}
