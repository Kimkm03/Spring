package com.shop.shopping.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDTO {
	 	private Integer categorynum;
	 	private String pname;
	    private Integer price;
	    private int discountprice;
	    private Integer pcount;
	    private List<String> size;
	    private List<String> color;
	    private String comment;
	    private byte[] picture;
	    private String productstate;
}
