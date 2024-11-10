package com.shop.shopping.service;

import com.shop.shopping.model.Cart;
import com.shop.shopping.model.CartItem;
import com.shop.shopping.model.Member;
import com.shop.shopping.model.Product;
import com.shop.shopping.repository.CartRepository;
import com.shop.shopping.repository.MemberRepository;
import com.shop.shopping.repository.ProductRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    
    @Autowired
    public CartService(MemberRepository memberRepository, ProductRepository productRepository, CartRepository cartRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public int addCartItem(int memberId, int productId, CartItem cartItem) {
        try {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
            
            Cart cart = member.getCart();
            if (cart == null) {
                cart = new Cart();
                cart.setMember(member);
                cartRepository.save(cart); // 새로 생성된 Cart 저장
            }
            
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
            
            cartItem.setProduct(product); // Product 객체 설정
            
            int discountPrice = product.getDiscountprice();
            if (discountPrice > 0) {
                // 할인 가격이 있는 경우, 할인 가격을 설정합니다.
                cartItem.setPrice(discountPrice);
            } else {
                // 할인 가격이 없는 경우, 원래 가격을 설정합니다.
                cartItem.setPrice(product.getPrice());
            }
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
            cartRepository.save(cart);
            
            return 1; // 성공 시 1을 반환
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // 실패 시 -1을 반환
        }
    }
    
    public int getCartItemCount(String memberId) {
        try {
            Cart cart = memberRepository.findByMemid(memberId)
                    .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다.")).getCart();

            if (cart == null) {
                return 0; // 장바구니가 비어있을 경우 0 반환
            } else {
                return cart.getItems().size(); // 장바구니에 담긴 상품 수 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // 오류 발생 시 -1 반환
        }
    }
    
    public int getCartItemId(Integer memberId) {
        try {
            // memberId를 기반으로 카트 번호 조회 로직을 작성
            int cartId = cartRepository.findCartIdByMemberId(memberId);
            return cartId; // 조회된 카트 번호 반환
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // 예외 발생 시 -1 반환
        }
    }
}
