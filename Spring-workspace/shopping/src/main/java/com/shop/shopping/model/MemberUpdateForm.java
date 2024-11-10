package com.shop.shopping.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberUpdateForm {
    private String mempwd, pnumber, email, firstaddress, secondaddress;
    private int postcode;
}
