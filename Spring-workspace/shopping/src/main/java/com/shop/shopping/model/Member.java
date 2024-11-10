package com.shop.shopping.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "member")
public class Member {
    public void MemberUpdate(MemberUpdateForm memberUpdateForm){
        this.mempwd = memberUpdateForm.getMempwd();
        this.pnumber = memberUpdateForm.getPnumber();
        this.email = memberUpdateForm.getEmail();
        this.postcode = memberUpdateForm.getPostcode();
        this.firstaddress = memberUpdateForm.getFirstaddress();
        this.secondaddress = memberUpdateForm.getSecondaddress();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memnum;
    
    @Column(length = 50, nullable = false, unique = true)
    private String memid;

    @Column(length = 50, nullable = false)
    private String mempwd;

    @Column(length = 20, nullable = false)
    private String memname;

    @Column(length = 13, nullable = false)
    private String pnumber;

    @Column(length = 60, nullable = false)
    private String email;

    @Column(length = 10, nullable = false)
    private String memrank;

    @CreationTimestamp
	private Timestamp createDate;

    @Column(nullable = false)
    private int mileage;

    @Column(nullable = false)
    private int postcode;
    
    @Column(length = 75, nullable = false)
    private String firstaddress;

    @Column(length = 75, nullable = false)
    private String secondaddress;
    
    @Column(nullable = false)
    private boolean admintype;
    
    @OneToMany(mappedBy = "member")
    private List<Product> products;
    
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
