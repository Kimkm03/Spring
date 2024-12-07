package com.shop.shopping.controller.api;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shop.shopping.dto.DeliverySearchCriteria;
import com.shop.shopping.dto.OrderRequest;
import com.shop.shopping.service.IamportService;
import com.shop.shopping.service.OrderService;
import com.shop.shopping.dto.PaymentResponse;
import com.shop.shopping.dto.ResponseDto;
import com.shop.shopping.model.Order;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final IamportService iamportService; // 가정: 결제 검증 서비스
    private final OrderService orderService; // 가정: 주문 서비스

    public OrderController(IamportService iamportService, OrderService orderService) {
        this.iamportService = iamportService;
        this.orderService = orderService;
    }

    @PostMapping("/verifyIamportAndAddOrder/{impUid}")
    public ResponseEntity<Map<String, String>> verifyIamportAndAddOrder(
            @PathVariable String impUid, 
            @RequestBody OrderRequest orderRequest) {
        try {
            // 1. 포트원 API를 사용해 액세스 토큰 발급
            String accessToken = iamportService.getAccessToken();

            // 2. 포트원 결제내역 조회
            PaymentResponse paymentResponse = iamportService.getPaymentDetails(impUid, accessToken);

            // 3. 결제 정보 검증
            Map<String, Object> paymentData = iamportService.verifyPayment(impUid, accessToken);

            // 4. 결제 상태 및 금액 확인
            String status = (String) paymentData.get("status");
            Integer paidAmount = (Integer) paymentData.get("amount");

            // 5. 결제 상태와 금액을 체크하고 주문 처리
            if ("PAID".equals(status)) {
                orderService.saveOrder(orderRequest); // DB에 주문 저장

                // 성공 응답 반환
                Map<String, String> response = new HashMap<>();
                response.put("message", "주문이 성공적으로 완료되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap("message", "결제 금액 또는 상태가 일치하지 않습니다."));
            }

        } catch (IOException e) {
            // I/O 오류 발생 시
            System.err.println("I/O Error during payment verification: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "결제 검증 중 I/O 오류가 발생했습니다."));
        } catch (Exception e) {
            // 일반 예외 발생 시
            System.err.println("Payment verification failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "결제 검증 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }
    
    @PutMapping("/updateOrder/{id}")
    public ResponseEntity<ResponseDto<String>> updateOrder(@PathVariable Integer id, @RequestParam String orderStatus){
    	try {
            orderService.updateOrder(id, orderStatus);
            return ResponseEntity.ok(new ResponseDto<>(1, "Order status updated"));
        } catch (IllegalArgumentException e) {
        	return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(-1, "Invalid order status"));
        }
    }
    
    // 주문 필터링 조회
    @PostMapping("/order/search")
    public ResponseEntity<List<Order>> searchOrders(@RequestBody DeliverySearchCriteria criteria) {
    	// 상태값이 "전체"인 경우 null로 처리
        String orderStatus = criteria.getOrderStatus().equals("전체") ? null : criteria.getOrderStatus();

        List<Order> orders = orderService.searchOrders(
            criteria.getUserCode(), // 추가된 userCode
            orderStatus,
            criteria.getStartDate(),
            criteria.getEndDate()
        );
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("orders/search")
    public List<Order> searchProducts(@RequestParam String keyword, Integer code) {
        return orderService.searchOrders(keyword, code);
    }
    
    @GetMapping("/orderdetail/{id}")
    public List<Order> getOrderdetail(@PathVariable Integer id){
    	return orderService.getOrderByUserCode(id);
    }
    
    @GetMapping("/orderstatusdetail/{id}")
    public List<Order> getOrderStatusdetail(@PathVariable Integer id){
    	return orderService.getOrderByUserCodeAndOrderStatus(id);
    }
    
    @GetMapping("/allOrders")
    public List<Order> getAllOrders(){
    	return orderService.getAllOrders();
    }
    
    @GetMapping("/todayOrders")
    public Integer todayOrders() {
    	return orderService.getOrderDateToday();
    }
    
    @GetMapping("/totalOrders")
    public Integer getTotalOrders() {
    	return orderService.getTotalOrders();
    }
}
