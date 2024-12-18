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
@Table(name = "review")
public class Review {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	private int memnum;
    private int productCode;
    private int starcnt;
    
    private String productColor;
    private String productSize;
    
    @Column(length = 500)
    private String content;
    
    private String delveryreply;
    private String takeoutreply;
    
    private boolean state;
    
    @Column(columnDefinition = "LONGBLOB")
    private byte[] picture;
    
    @CreationTimestamp
	private Timestamp createDate;
}
