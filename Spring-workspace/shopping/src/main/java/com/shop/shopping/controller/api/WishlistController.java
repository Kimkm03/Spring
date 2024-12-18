package com.shop.shopping.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shopping.dto.ResponseDto;
import com.shop.shopping.model.Wishlist;
import com.shop.shopping.service.WishlistService;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "http://localhost:3000")
public class WishlistController {
	private final WishlistService wishlistService;
	
	@Autowired
	public WishlistController(WishlistService wishlistService) {
		this.wishlistService = wishlistService;
	}
	
	@PostMapping("/save")
    public ResponseDto<Integer> save(@RequestBody Wishlist wishlist){
        try {      
            int result = wishlistService.save(wishlist);
            return new ResponseDto<>(HttpStatus.OK.value(), result);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), -1);
        }
    }
	
	@PostMapping("/delete/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable Integer id) {
    	int result = wishlistService.deleteById(id);
        if (result == 1) {
            return new ResponseDto<>(HttpStatus.OK.value(), result); // 1 indicates successful deletion
        } else {
            return new ResponseDto<>(HttpStatus.NOT_FOUND.value(), -1); // -1 indicates failure due to member not found
        }
    }
	
	@PostMapping("/deleteStyle/{id}")
    public ResponseDto<Integer> deleteByStyleCode(@PathVariable Integer id) {
    	int result = wishlistService.deleteByStyleCode(id);
        if (result == 1) {
            return new ResponseDto<>(HttpStatus.OK.value(), result); // 1 indicates successful deletion
        } else {
            return new ResponseDto<>(HttpStatus.NOT_FOUND.value(), -1); // -1 indicates failure due to member not found
        }
    }
	
	// 관심 목록에 상품이 존재하는지 확인하는 API
    @PostMapping("/check/{styleCode}/{memberCode}")
    public boolean checkIfExists(@PathVariable int styleCode, @PathVariable int memberCode) {
        return wishlistService.isProductInWishlist(styleCode, memberCode);
    }
    
    @GetMapping("/search")
    public List<Wishlist> searchWishlist(@RequestParam("keyword") String keyword, @RequestParam("memberCode") int memberCode) {
        return wishlistService.getWishlistByMemberCodeAndKeyword(memberCode, keyword);
    }
	
	@GetMapping("/productCode/{memberCode}")
	public List<Wishlist> getWishListByProductCode(@PathVariable Integer memberCode){
		return wishlistService.getProductCodeByWishList(memberCode);
	}
	
	@GetMapping("/styleCode/{memberCode}")
	public List<Wishlist> getWishListByStyleCode(@PathVariable Integer memberCode){
		return wishlistService.getStyleCodeByWishList(memberCode);
	}
}
