package com.shop.shopping.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shopping.dto.ResponseDto;
import com.shop.shopping.dto.ReviewState;
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
            Review review = objectMapper.readValue(reviewJson, Review.class);;
            
            // 이미지 파일 처리
            if (picture != null && !picture.isEmpty()) {
                review.setPicture(picture.getBytes());
            }
            review.setState(true);
            int result = reviewService.saveReview(review);
            return new ResponseDto<>(HttpStatus.OK.value(), result);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), -1);
        }
    }
	
	// productCode와 memnum을 받아 리뷰 상태 반환
    @GetMapping("/product/{productCode}/memnum/{memnum}")
    public ReviewState getReviewState(@PathVariable Integer productCode, @PathVariable Integer memnum) {
        boolean reviewState = reviewService.checkReviewState(productCode, memnum);
        return new ReviewState(reviewState);
    }
	
	@GetMapping("/product/{productCode}")
	public List<Review> getReviewByProductCode (@PathVariable Integer productCode){
		return reviewService.getProductCodeByReview(productCode);
	}
	
	@GetMapping("/member/{memnum}")
	public List<Review> getReviewByMemNum(@PathVariable Integer memnum){
		return reviewService.getMemNumByReview(memnum);
	}
	
	@GetMapping("/all")
    public List<Review> getReviewAll(){
    	return reviewService.getAllReview();
    }
	
	@GetMapping("/{reviewId}/picture")
    public ResponseEntity<byte[]> getReviewPicture(@PathVariable int reviewId) {
        Optional<Review> reviewOptional = reviewService.getReviewById(reviewId);
        if (reviewOptional.isPresent()) {
            byte[] picture = reviewOptional.get().getPicture();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // 이미지 타입에 따라 적절히 설정

            return new ResponseEntity<>(picture, headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}
