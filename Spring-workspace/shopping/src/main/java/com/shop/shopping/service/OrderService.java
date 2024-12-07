package com.shop.shopping.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.shopping.dto.OrderRequest;
import com.shop.shopping.model.Order;
import com.shop.shopping.model.OrderStatus;
import com.shop.shopping.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setUserCode(orderRequest.getUserCode());
        order.setProductCode(orderRequest.getProductCode());
        order.setPostcode(orderRequest.getPostcode());
        order.setReceiverName(orderRequest.getReceiverName());
        order.setReceiverPhone(orderRequest.getReceiverPhone());
        order.setReceiverEmail(orderRequest.getReceiverEmail());
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setProductSize(orderRequest.getProductSize());
        order.setProductColor(orderRequest.getProductColor());
        order.setRequest(orderRequest.getRequest());
        order.setCouponCode("0");
        order.setImpUid(orderRequest.getImpUid());
        order.setOrderPrice(orderRequest.getOrderPrice());
        order.setCount(orderRequest.getCount());
        order.setOrderStatus(OrderStatus.PAID);

        orderRepository.save(order);
    }
    
    public void updateOrder(Integer orderId, String orderStatus) {
    	Optional<Order> optionalOrder = orderRepository.findById(orderId);
    	Order order = optionalOrder.orElseThrow(() -> new NoSuchElementException("주문을 찾을 수 없습니다."));

    	OrderStatus status = OrderStatus.valueOf(orderStatus);
    	order.setOrderStatus(status);
        
        orderRepository.save(order);
    }
    
    public List<Order> searchOrders(Integer userCode, String orderStatus, String startDate, String endDate) {
        return orderRepository.findOrdersByCriteria(userCode, orderStatus, startDate, endDate);
    }
    
    public List<Order> getOrderByUserCode(Integer id) {
        return orderRepository.findByUserCode(id);
    }
    
    public List<Order> getOrderByUserCodeAndOrderStatus(Integer id){
    	return orderRepository.findByUserCodeAndOrderStatus(id);
    }
    
    public List<Order> searchOrders(String keyword, Integer code) {
        return orderRepository.findByProductNameContaining(keyword, code);
    }
    
    public List<Order> getAllOrders(){
    	return orderRepository.findAll();
    }
    
    public Integer getOrderDateToday() {
    	return orderRepository.countOrderDateToday();
    }
    
    public Integer getTotalOrders() {
    	return orderRepository.getTotalOrdersCount();
    }
}
