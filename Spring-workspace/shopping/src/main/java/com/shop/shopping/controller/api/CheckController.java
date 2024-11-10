package com.shop.shopping.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shopping.service.MemberService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CheckController {

    private final MemberService memberService;

    @Autowired
    public CheckController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/api/check/{memid}")
    public ResponseEntity<Boolean> checkDuplicate(@PathVariable String memid) {
        boolean isDuplicate = memberService.validateDuplicateMember(memid);
        return ResponseEntity.ok(isDuplicate);
    }
}
