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
public class AddProductDTO {
	 	private Integer categorynum;
	 	private Integer subcategorynum;
	 	private String pname;
	    private Integer price;
	    private Integer pcount;
	    private List<String> size;
	    private List<String> color;
	    private String comment;
	    private byte[] picture;
	    private String productstate;
}
