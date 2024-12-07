package com.shop.shopping.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WishlistRequest {
	private int styleCode;
    private int memberCode;
}
