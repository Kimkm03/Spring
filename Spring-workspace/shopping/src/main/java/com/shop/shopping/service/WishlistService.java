package com.shop.shopping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.shopping.model.Review;
import com.shop.shopping.model.Wishlist;
import com.shop.shopping.repository.WishlistRepository;

import jakarta.transaction.Transactional;

@Service
public class WishlistService {
private final WishlistRepository wishRepository;
	
	@Autowired
	public WishlistService(WishlistRepository wishRepository) {
		this.wishRepository = wishRepository;
	}
	
	@Transactional
	public int save(Wishlist wishlist) {
		try {
			wishRepository.save(wishlist);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
    		System.out.println("WishlistService : 등록하기() : " + e.getMessage());
		}
		return -1;
	}
	
	public List<Wishlist> getProductCodeByWishList(Integer memberCode){
		return wishRepository.findByProductCodeIsNotNull(memberCode);
	}
	
	public List<Wishlist> getStyleCodeByWishList(Integer memberCode){
		return wishRepository.findByStyleCodeIsNotNull(memberCode);
	}
	
	@Transactional
    public int deleteById(Integer id) {
    	try {
    		wishRepository.deleteById(id);
            return 1; // Successful deletion
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("WishlistService : delete() : " + e.getMessage());
            return -1; // Error occurred during deletion
        }    
    }
	
	@Transactional
    public int deleteByStyleCode(Integer styleCode) {
    	try {
    		Integer id = wishRepository.findwishIdByStyleCode(styleCode);
    		wishRepository.deleteById(id);
            return 1; // Successful deletion
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("WishlistService : delete() : " + e.getMessage());
            return -1; // Error occurred during deletion
        }    
    }
	
	// 관심 목록에 해당 상품이 존재하는지 확인하는 메소드
    public boolean isProductInWishlist(int styleCode, int memberCode) {
        return wishRepository.existsByStyleCodeAndMemberCode(styleCode, memberCode);
    }
    
    public List<Wishlist> getWishlistByMemberCodeAndKeyword(int memberCode, String keyword) {
        return wishRepository.findByMemberCodeAndProductNameContaining(memberCode, keyword);
    }
}
