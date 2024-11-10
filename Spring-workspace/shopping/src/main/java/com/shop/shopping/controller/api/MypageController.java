package com.shop.shopping.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopping.model.Member;
import com.shop.shopping.service.MemberService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class MypageController {

    private final MemberService memberService;

    @Autowired
    public MypageController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/mypage/{memid}")
    public ResponseEntity<Object> getMyPageDetails(@PathVariable("memid") String memid) {
        Member member = memberService.findMemberByMemid(memid).orElse(null);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
    }
}
