package com.shop.shopping.dto;

import com.shop.shopping.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemDTO {
    private int id;
    private int productId;
    private String productName;
    private int quantity;
    private String size;
    private String color;
    private int count;
    private int price;

    public CartItemDTO(int id, Product product, int quantity, String size, String color, int count, int price) {
        this.id = id;
        this.productId = product.getPnum();
        this.productName = product.getPname();
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.count = count;
        this.price = price;
    }
}
