package com.shop.shopping.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Style")
public class Style {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(columnDefinition = "LONGBLOB")
    private byte[] picture;
	
	private String gender;
	
	private String season;
	
	private String tpo;
	
	private int height;
	
	private int weight;
	
	private String uppercolor;
	
	private String uppersize;
	
	private String lowercolor;
	
	private String lowersize;
	
	private int memnum;
	
	private int upperpnum;
	private int lowerpnum;
	
	@CreationTimestamp
	private Timestamp createDate;
}
