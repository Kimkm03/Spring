package com.shop.shopping.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shopping.dto.ResponseDto;
import com.shop.shopping.dto.StyleDto;
import com.shop.shopping.model.Style;
import com.shop.shopping.service.StyleService;

@RestController
@RequestMapping("/api/style")
@CrossOrigin(origins = "http://localhost:3000")
public class StyleController {
	private final StyleService styleService;
	
	@Autowired
	public StyleController(StyleService styleService) {
		this.styleService = styleService;
	}
	
	@PostMapping("/save")
    public ResponseDto<Integer> save(
            @RequestParam("style") String styleJson,
            @RequestParam("picture") MultipartFile picture) {
        try {
            // Review 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            Style style = objectMapper.readValue(styleJson, Style.class);
            
            // 이미지 파일 처리
            if (picture != null && !picture.isEmpty()) {
            	style.setPicture(picture.getBytes());
            }
            int result = styleService.save(style);
            return new ResponseDto<>(HttpStatus.OK.value(), result);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), -1);
        }
    }
	
	@GetMapping("/filter")
    public List<Style> getFilteredStyles(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String season,
            @RequestParam(required = false) String tpo,
            @RequestParam(required = false) String uppercolor) {
		return styleService.getFillterStyle(gender, season, tpo, uppercolor);
    }
	
	@GetMapping("/all")
	public List<Style> getAllStyle(){
		return styleService.getAllStyle();
	}
	
	@GetMapping("/{Id}")
	public Optional<Style> getStyleById(@PathVariable int Id){
		return styleService.getStyleById(Id);
	}
	
	@GetMapping("member/{memnum}")
	public List<Style> getStyleByMemnum(@PathVariable Integer memnum){
		return styleService.getMemNum(memnum);
	}
	
	@GetMapping("/{Id}/picture")
    public ResponseEntity<byte[]> getStylePicture(@PathVariable int Id) {
        Optional<Style> styleOptional = styleService.getStyleById(Id);
        if (styleOptional.isPresent()) {
            byte[] picture = styleOptional.get().getPicture();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // 이미지 타입에 따라 적절히 설정

            return new ResponseEntity<>(picture, headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}
