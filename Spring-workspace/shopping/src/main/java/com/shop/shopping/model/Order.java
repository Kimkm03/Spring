package com.shop.shopping.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userCode;
    private int productCode;
    private String receiverName;
    private int postcode;
    private String shippingAddress;
    private String receiverPhone;
    private String receiverEmail;
    private String productSize;
    private String productColor;
    private String request;
    private String couponCode;
    private String impUid;
    private int orderPrice;
    private int count;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    
    @CreationTimestamp
	private Timestamp orderDate;

}
