package com.shop.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.shopping.model.Product;
import com.shop.shopping.model.Review;
import com.shop.shopping.repository.ReviewRepository;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {
private final ReviewRepository reviewRepository;
	
	@Autowired
	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@Transactional
	public int saveReview(Review review) {
		try {
			reviewRepository.save(review);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
    		System.out.println("ReviewService : 후기작성() : " + e.getMessage());
		}
		return -1;
	}
	
	// productCode와 memnum을 기준으로 리뷰 상태 확인
    public boolean checkReviewState(Integer productCode, Integer memnum) {
        // 특정 productCode와 memnum에 해당하는 리뷰 조회
        Review review = reviewRepository.findByProductCodeAndMemnum(productCode, memnum);
        // 리뷰가 null인지 확인하고 상태 반환
        if (review != null) {
            return review.isState(); // 리뷰가 존재하면 state 반환
        }

        // 리뷰가 없으면 false 반환
        return false;
    }
	
	public List<Review> getProductCodeByReview(Integer productCode){
		return reviewRepository.findByProductCode(productCode);
	}
	
	public List<Review> getMemNumByReview(Integer memnum){
		return reviewRepository.findByMemnum(memnum);
	}
	
	public Optional<Review> getReviewById(int reviewId) {
		return reviewRepository.findById(reviewId);
	}
	
	public List<Review> getAllReview(){
		return reviewRepository.findAll();
	}
}
