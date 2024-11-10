package com.shop.shopping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

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
    //private String couponCode;
    private String impUid;
    private int orderPrice;
    private int count;
}
