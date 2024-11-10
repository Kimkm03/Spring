package com.shop.shopping.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shopping.dto.UpdateProductDTO;
import com.shop.shopping.dto.AddProductDTO;
import com.shop.shopping.dto.ResponseDto;
import com.shop.shopping.model.Product;
import com.shop.shopping.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseDto<Integer> save(@RequestParam("product") String productJson,
                                     @RequestParam("picture") MultipartFile picture,
                                     @RequestParam("memberId") int memberId) {
        try {
            // 제품 JSON 역직렬화
            ObjectMapper objectMapper = new ObjectMapper();
            AddProductDTO productDTO = objectMapper.readValue(productJson, AddProductDTO.class);

            // 관리자 타입 확인
            boolean isAdmin = productService.isAdmin(memberId);
            if (!isAdmin) {
                return new ResponseDto<>(HttpStatus.FORBIDDEN.value(), -1);
            }

            // 이미지 파일 처리
            if (picture != null && !picture.isEmpty()) {
                productDTO.setPicture(picture.getBytes());
            }

            // ProductDTO를 Product 객체로 변환
            Product product = addConvertToEntity(productDTO);

            // 상품 상태 설정
            product.setProductstate("판매중");
            product.setDeliverystate("입금전");
            product.setDiscountprice(0);

            // 상품 저장 및 결과 반환
            int result = productService.saveProduct(product, memberId);
            return new ResponseDto<>(HttpStatus.OK.value(), result);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), -1);
        }
    }
    
    private Product addConvertToEntity(AddProductDTO productDTO) {
        Product product = new Product();
        product.setCategorynum(productDTO.getCategorynum());
        product.setSubcategorynum(productDTO.getSubcategorynum());
        product.setPname(productDTO.getPname());
        product.setPrice(productDTO.getPrice());
        product.setPcount(productDTO.getPcount());
        product.setSize(productDTO.getSize());
        product.setColor(productDTO.getColor());
        product.setComment(productDTO.getComment());
        product.setPicture(productDTO.getPicture()); // byte[] 형태로 설정

        return product;
    }
    
    
    @PutMapping("products/{productId}")
    public ResponseEntity<ResponseDto<Integer>> updateProductState(@PathVariable int productId,
                                                                    @RequestBody UpdateProductDTO productDTO) {
        try {
            Optional<Product> productOptional = productService.getProductById(productId);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                product.setProductstate(productDTO.getProductstate()); // 상태 업데이트
                product.setDiscountprice(productDTO.getDiscountprice());

                // ProductService를 통해 상품 정보를 업데이트하고 결과를 반환
                int result = productService.saveProduct(product);
                return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), result));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), -1));
        }
    }
    
    @PutMapping("products/{productCode}/update-quantity")
    public ResponseEntity<?> updateProductQuantity(@PathVariable int productCode, @RequestBody Map<String, Integer> orderQuantity) {
    	try {
    	    Optional<Product> productOptional = productService.getProductById(productCode);
    	    if (productOptional.isPresent()) {
    	        Product product = productOptional.get();

    	        // 수량 감소 처리
    	        int updatedQuantity = productService.decreaseProductQuantity(productCode, orderQuantity.get("orderQuantity"));
    	        
    	        // 수량이 0인 경우 productstate를 "품절"로 변경
    	        if (product.getPcount() <= 0) {
    	            product.setProductstate("품절");
    	            productService.saveProduct(product); // 변경된 productstate 저장
    	        }
    	        return ResponseEntity.ok("수량이 성공적으로 업데이트되었습니다.");
    	    } else {
    	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 상품을 찾을 수 없습니다.");
    	    }
    	} catch (Exception e) {
    	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수량 업데이트 중 오류 발생");
    	}

    }
    
    @PutMapping("products/{pnum}/increment-views")
    public void incrementViews(@PathVariable int pnum) {
        productService.incrementViews(pnum);
    }
    
    @GetMapping("products/best")
    public List<Product> getBestProducts() {
        return productService.getProductsByViewsGreaterThanEqual();
    }
    
    @GetMapping("products/new")
    public List<Product> getNewProducts() {
        return productService.getProductsAddedInLast7Days();
    }
    
    @GetMapping("products/all")
    public List<Product> getAllProducts(@RequestParam(required = false) Integer categoryId) {
        return productService.getAllProducts(categoryId);
    }
    
    @GetMapping("products/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        return productService.searchProducts(keyword);
    }

    @GetMapping("products/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }
    
    
    
    @GetMapping("/products")
    public List<Product> getProductsByCategory(@RequestParam int categorynum) {
        return productService.getProductsByCategory(categorynum);
    }
    
    @GetMapping("/products/{productId}/picture")
    public ResponseEntity<byte[]> getProductPicture(@PathVariable int productId) {
        Optional<Product> productOptional = productService.getProductById(productId);
        if (productOptional.isPresent()) {
            byte[] picture = productOptional.get().getPicture();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // 이미지 타입에 따라 적절히 설정

            return new ResponseEntity<>(picture, headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}
