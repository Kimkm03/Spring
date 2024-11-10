package com.shop.shopping.model;

public enum OrderStatus {
	PENDING,   // 결제 대기 중
    PAID,      // 결제 완료
    PREPARING_SHIPMENT, // 배송준비중
    IN_TRANSIT, // 배송중
    DELIVERED, // 배송완료
    REFUNDED, // 환불
    EXCHANGED // 교환
}
