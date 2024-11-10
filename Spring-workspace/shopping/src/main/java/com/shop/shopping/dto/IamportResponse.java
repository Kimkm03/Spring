package com.shop.shopping.dto;

public class IamportResponse {
	private int amount;

    public IamportResponse(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
