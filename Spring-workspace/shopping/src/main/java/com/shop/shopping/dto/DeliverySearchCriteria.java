package com.shop.shopping.dto;

import lombok.Data;

@Data
public class DeliverySearchCriteria {
	private Integer userCode;
    private String orderStatus; // 주문 상태
    private String startDate;   // 검색 시작 날짜
    private String endDate;     // 검색 종료 날짜
}

