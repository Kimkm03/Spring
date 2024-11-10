package com.shop.shopping.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pnum;

    @Column(nullable = false)
    private String pname;

    @Column(nullable = false)
    private int price;

    @ElementCollection
    @CollectionTable(name = "Product_Size", joinColumns = @JoinColumn(name = "pnum"))
    @Column(name = "size")
    private List<String> size;

    @Column(nullable = false)
    private int categorynum;
    
    @Column(nullable = false)
    private int subcategorynum;
    
    @Column()
    private int discountprice;
    
    @ElementCollection
    @CollectionTable(name = "Product_Color", joinColumns = @JoinColumn(name = "pnum"))
    @Column(name = "color")
    private List<String> color;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] picture;

    @Column()
    private String comment;
    
    @Column()
    private String productstate; 
    
    @Column(nullable = false)
    private int pcount;
    
    @CreationTimestamp
	private Timestamp pcreateDate;
    
    @Column(nullable = false)
    private String deliverystate;
    
    @Column()
    private int views;
    
    @ManyToOne
	@JoinColumn(name="memnum", nullable = false)
    @JsonIgnore // 회원과의 순환 참조를 방지하기 위해 직렬화에서 회원을 무시합니다.
	private Member member;
    
    @OneToMany(mappedBy = "product")
    private Set<CartItem> cartItems;
}
