package com.shop.shopping.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentMethod {
    private String type;
    @JsonProperty("card")
    private Card card;

    @JsonProperty("approvalNumber")
    private String approvalNumber;
}

