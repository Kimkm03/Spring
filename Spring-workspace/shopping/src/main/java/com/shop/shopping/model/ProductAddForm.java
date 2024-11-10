package com.shop.shopping.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductAddForm {
    private String pname;
    private int price;
    private int amount;
    private String size;
    private int categorynum;
    private byte[] picture;
    private String comment;
}
