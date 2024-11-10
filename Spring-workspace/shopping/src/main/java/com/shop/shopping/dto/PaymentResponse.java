package com.shop.shopping.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // 알지 못하는 필드는 무시
public class PaymentResponse {
    
    @JsonProperty("status")
    private String status;

    @JsonProperty("id")
    private String id;

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("merchantId")
    private String merchantId;

    @JsonProperty("storeId")
    private String storeId;
}