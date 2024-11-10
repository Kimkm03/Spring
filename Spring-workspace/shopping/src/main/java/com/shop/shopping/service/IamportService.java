package com.shop.shopping.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shopping.dto.PaymentResponse;

@Service
public class IamportService {

    // Portone API 인증 정보
    private final String PORTONE_API_SECRET = "MEiyEBW9ROWz855hMyuClLO0QiA8XjGHW532uye5AYWkgWa7HSynkV6Tq6uTEI7SW2vfiT1PEHuNigvF"; // 실제 Portone API Secret으로 변경해야 합니다.
    //private final String PORTONE_API_SECRET = "yy2sZ9EwqIiI/rrnerrj16VaYhcBt55N6oGVlOtYotgXv/OgNRk0bMvAawkeT6Z2CnjbG7Sfclrg7cnyMaiD2A==";
    private final String storeId = "store-cc5c0f67-cf0f-45b3-bc8e-5b2b46921971";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper 인스턴스 추가

    // 결제 정보 조회
    public Map<String, Object> verifyPayment(String paymentId, String accessToken) throws IOException {
        // 결제 정보 가져오기
        PaymentResponse paymentResponse = getPaymentDetails(paymentId, accessToken);

        // 응답을 Map으로 변환하여 리턴
        return convertPaymentResponseToMap(paymentResponse);
    }
    
    public String getAccessToken() throws Exception{
        String url = "https://api.portone.io/v2/signin/api-key";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("api_key", PORTONE_API_SECRET);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            // 정상적으로 토큰을 받았을 경우
            return (String) response.getBody().get("access_token");
        } else {
            throw new Exception("Failed to get access token. Status code: " + response.getStatusCode());
        }
    }
    
    public PaymentResponse getPaymentDetails(String paymentId, String accessToken) throws IOException {
        try {
            // URL 설정
            String url = "https://api.portone.io/payments/" + paymentId;

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            // 요청 보내기
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // 응답 본문 가져오기
            String responseBody = response.getBody();
            
            // 응답 로그 출력
            System.out.println("Response Body: " + responseBody);

            // JSON 형식인지 확인
            if (responseBody != null && responseBody.startsWith("{") && responseBody.endsWith("}")) {
                // JSON을 객체로 변환
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(responseBody);

                // 필요한 정보 추출 (상위 레벨에서)
                String status = root.get("status").asText();
                String id = root.get("id").asText();
                String transactionId = root.get("transactionId").asText();
                String merchantId = root.get("merchantId").asText();
                String storeId = root.get("storeId").asText();

                // PaymentResponse 객체 생성 및 반환
                PaymentResponse paymentResponse = new PaymentResponse();
                paymentResponse.setStatus(status);
                paymentResponse.setId(id);
                paymentResponse.setTransactionId(transactionId);
                paymentResponse.setMerchantId(merchantId);
                paymentResponse.setStoreId(storeId);

                return paymentResponse;
            } else {
                throw new IOException("Invalid JSON response: " + responseBody);
            }
        } catch (HttpClientErrorException.Unauthorized e) {
            // 토큰 만료 예외 처리
            System.out.println("Token expired, attempting to refresh...");
            
            try {
                String newAccessToken = getAccessToken(); // 새로운 토큰 발급
                return getPaymentDetails(paymentId, newAccessToken); // 재시도
            } catch (Exception ex) {
                // 새로운 토큰 발급 중 발생한 예외 처리
                throw new IOException("Failed to refresh access token: " + ex.getMessage(), ex);
            }
        }
    }

    // PaymentResponse를 Map으로 변환
    private Map<String, Object> convertPaymentResponseToMap(PaymentResponse paymentResponse) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", paymentResponse.getStatus());
        responseMap.put("id", paymentResponse.getId());
        responseMap.put("transactionId", paymentResponse.getTransactionId());
        responseMap.put("merchantId", paymentResponse.getMerchantId());
        responseMap.put("storeId", paymentResponse.getStoreId());
        return responseMap;
    }
}
