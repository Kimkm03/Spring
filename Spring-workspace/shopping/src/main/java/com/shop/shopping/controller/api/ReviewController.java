package com.shop.shopping.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shopping.dto.ResponseDto;
import com.shop.shopping.model.Review;
import com.shop.shopping.service.ReviewService;

@RestController
@RequestMapping("/api/review")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
	private final ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	@PostMapping("/save")
    public ResponseDto<Integer> save(
            @RequestParam("review") String reviewJson,
            @RequestParam("picture") MultipartFile picture) {
        try {
            // Review 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            Review review = objectMapper.readValue(reviewJson, Review.class);
            
            // 이미지 파일 처리
            if (picture != null && !picture.isEmpty()) {
                review.setPicture(picture.getBytes());
            }
            int result = reviewService.saveReview(review);
            return new ResponseDto<>(HttpStatus.OK.value(), result);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), -1);
        }
    }
}
