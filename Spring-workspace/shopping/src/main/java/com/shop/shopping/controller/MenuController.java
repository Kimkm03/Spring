package com.shop.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("top")
    public String top(){
        return "top";
    }

    @GetMapping("best")
    public String best(){
        return "best";
    }

}
