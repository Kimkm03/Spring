package com.shop.shopping.service;

import com.shop.shopping.model.Member;
import com.shop.shopping.model.Product;
import com.shop.shopping.repository.MemberRepository;
import com.shop.shopping.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, MemberRepository memberRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public boolean validateDuplicateProduct(Product product) {
        return productRepository.findById(product.getPnum()).isPresent();
    }

    public boolean isAdmin(int memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        return member.isAdmintype();
    }

    public int saveProduct(Product product, int memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        product.setMember(member);
        Product savedProduct = productRepository.save(product);
        return savedProduct.getPnum();
    }
    
    public int saveProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return savedProduct != null ? 1 : 0;
    }
    
    public int decreaseProductQuantity(int productCode, int orderQuantity) {
        Product product = productRepository.findById(productCode).orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        
        // 재고 차감
        int newQuantity = product.getPcount() - orderQuantity;
        product.setPcount(newQuantity);
        
        // 상품 저장
        productRepository.save(product);
        return newQuantity;
    }
    
    public void incrementViews(int pnum) {
        Optional<Product> productOptional = productRepository.findById(pnum);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setViews(product.getViews() + 1);
            productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found with id: " + pnum);
        }
    }
    
    public List<Product> getProductsByViewsGreaterThanEqual() {
        return productRepository.findProductsByViewsGreaterThanEqual();
    }
    
    public List<Product> getProductsAddedInLast7Days() {
    	LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return productRepository.findProductsAddedInLast7Days(sevenDaysAgo);
    }
    
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }
    
    public List<Product> getAllProducts(Integer categoryId) {
        if (categoryId != null) {
            return productRepository.findByCategorynum(categoryId);
        }
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }
   
    public List<Product> getProductsByCategory(int categorynum) {
        return productRepository.findByCategorynum(categorynum);
    }

	public Optional<Product> getProductById(int productId) {
		return productRepository.findById(productId);
	}
}
