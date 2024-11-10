package com.shop.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
