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
@Table(name = "board")
public class Board {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userCode;
    private String title;
    
    @Column(length = 500)
    private String content;
    
    private String reply;
    private boolean state;
    
    @CreationTimestamp
	private Timestamp createDate;
}
