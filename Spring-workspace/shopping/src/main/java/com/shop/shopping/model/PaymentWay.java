package com.shop.shopping.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paymentway")
public class PaymentWay {

    @Id
    @Column(nullable = false)
    private int paymentcode;

    @Column(nullable = false)
    private String paymentname;

}
