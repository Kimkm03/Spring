package com.shop.shopping.dto;

public class TokenResponse {
	public static class Response {
        private String access_token;

        public String getAccessToken() {
            return access_token;
        }
    }

    private Response response;

    public Response getResponse() {
        return response;
    }
}
