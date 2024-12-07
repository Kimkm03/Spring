package com.shop.shopping.dto;

public class ReviewState {
	private boolean state;
	
	// boolean 값을 받는 생성자 추가
    public ReviewState(boolean state) {
        this.state = state;
    }

    // getter
    public boolean isState() {
        return state;
    }

    // setter
    public void setState(boolean state) {
        this.state = state;
    }
}
