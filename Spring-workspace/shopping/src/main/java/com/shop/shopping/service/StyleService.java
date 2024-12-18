package com.shop.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.shopping.model.Style;
import com.shop.shopping.repository.StyleRepository;

import jakarta.transaction.Transactional;

@Service
public class StyleService {
	private final StyleRepository styleRepository;
	
	@Autowired
	public StyleService(StyleRepository styleRepository) {
		this.styleRepository = styleRepository;
	}
	
	@Transactional
	public int save(Style style) {
		try {
			styleRepository.save(style);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
    		System.out.println("StyleService : 등록하기() : " + e.getMessage());
		}
		return -1;
	}
	
	public List<Style> getFillterStyle(String gender, String season, String tpo, String uppercolor){
		return styleRepository.findStylesByFilters(gender, season, tpo, uppercolor);
	}
	
	public List<Style> getAllStyle(){
		return styleRepository.findAll();
	}
	
	public Optional<Style> getStyleById(Integer id) {
        return styleRepository.findById(id);
    }
	
	public List<Style> getMemNum(Integer memnum){
		return styleRepository.findByMemNum(memnum);
	}
}
