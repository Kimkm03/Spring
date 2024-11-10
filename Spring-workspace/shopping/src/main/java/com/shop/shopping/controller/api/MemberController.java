package com.shop.shopping.controller.api;

import com.shop.shopping.dto.ResponseDto;
import com.shop.shopping.model.Member;
import com.shop.shopping.model.MemberUpdateForm;
import com.shop.shopping.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signup")
    public ResponseEntity<String> register() {
        return ResponseEntity.ok("Signup Page");
    }

    @PostMapping("/signup")
    public ResponseDto<Integer> save(@RequestBody Member member) {
        String memid = member.getMemid();
        if (memberService.validateDuplicateMember(memid)) {
            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), -1); // -1 indicates failure due to duplicate member
        } else {
            member.setMileage(0);
            member.setMemrank("Bronze");
            member.setAdmintype(false);
            int result = memberService.saveMember(member);
            return new ResponseDto<>(HttpStatus.OK.value(), result);
        }
    }
    
    @PostMapping("/updateMember/{id}")
    public ResponseDto<Integer> updateMember(@PathVariable Integer id, @RequestBody MemberUpdateForm memberUpdateForm, HttpServletRequest request) {
        // 서비스 레이어의 메서드를 호출하여 회원 정보 업데이트를 수행합니다.
        Member updatedMember = memberService.updateMember(id, memberUpdateForm);
        if (updatedMember != null) {
            // 업데이트된 회원 정보를 세션에 저장합니다.
            HttpSession session = request.getSession();
            session.setAttribute("memberData", updatedMember);
            // 회원 정보 수정이 성공적으로 수행되었음을 응답합니다.
            return new ResponseDto<>(HttpStatus.OK.value(), 1);
        } else {
            return new ResponseDto<>(HttpStatus.NOT_FOUND.value(), -1);
        }
    }


    @PostMapping("/deleteMember/{id}")
    public ResponseDto<Integer> deleteMember(@PathVariable Integer id, HttpServletRequest request) {
    	int result = memberService.deleteMember(id);
        if (result == 1) {
        	HttpSession session = request.getSession();
            session.invalidate();
            return new ResponseDto<>(HttpStatus.OK.value(), result); // 1 indicates successful deletion
        } else {
            return new ResponseDto<>(HttpStatus.NOT_FOUND.value(), -1); // -1 indicates failure due to member not found
        }
    }

    
    @GetMapping("/memberInfo/{id}")
    public ResponseEntity<Object> getMemberInfo(@PathVariable("id") Integer id){
    	Member member = memberService.getMemberById(id).orElse(null);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
    }
    
    @GetMapping("/memberCount")
    public List<Member> getMemberCount(){
    	return memberService.getMemberCount();
    }
}
