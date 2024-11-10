package com.shop.shopping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentVerifyRequest {
	private int productCode;
    private String userCode;
    private int amount;

}
