package com.shop.shopping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {
	private String publisher;
    private String issuer;
    private String brand;
    private String type;
    private String ownerType;
    private String bin;
    private String name;
    private String number;
}
