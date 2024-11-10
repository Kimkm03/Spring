package com.shop.shopping.controller.api;

import com.shop.shopping.model.Member;
import com.shop.shopping.model.MemberLoginForm;
import com.shop.shopping.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    private final MemberService memberService;

    @Autowired
    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Login Page");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginConfirm(@RequestBody MemberLoginForm memberLoginForm, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        Member member = memberService.getMemberByMemId(memberLoginForm.getMemid());
        if (member != null && member.getMempwd().equals(memberLoginForm.getMempwd())) {
            HttpSession session = request.getSession();
            session.setAttribute("memberData", member);
            response.put("memberData", member);
            response.put("status", "Login Success");
            response.put("admintype", member.isAdmintype());
            response.put("memname", member.getMemname());
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "로그인 실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @GetMapping("/api/userinfo/{memid}")
    public ResponseEntity<Object> getUserInfo(@PathVariable("memid") String memid) {
        Member userInfo = memberService.findMemberByMemid(memid).orElse(null);
        if (userInfo != null) {
            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃");
    }
}
