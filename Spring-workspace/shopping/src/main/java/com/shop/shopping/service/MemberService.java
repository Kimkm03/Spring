package com.shop.shopping.service;

import com.shop.shopping.model.Member;
import com.shop.shopping.model.MemberLoginForm;
import com.shop.shopping.model.MemberUpdateForm;
import com.shop.shopping.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean validateDuplicateMember(String memid) {
        Optional<Member> existingMember = memberRepository.findByMemid(memid);
        return existingMember.isPresent(); // 중복되는 경우 true, 중복되지 않는 경우 false 반환
    }

    @Transactional
    public int saveMember(Member member) {
    	try {
    		memberRepository.save(member);
    		return 1;
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("MemberService : 회원가입() : " + e.getMessage());
    	}
        return -1;    
    }

    
    
    public MemberLoginForm login(MemberLoginForm memberLoginForm) {
    	Optional<Member> memberOpt = memberRepository.findByMemidAndMempwd(memberLoginForm.getMemid(), memberLoginForm.getMempwd());
        return memberOpt.isPresent() ? memberLoginForm : new MemberLoginForm();
    }
    
    public Member getMemberByMemId(String memberId) {
        return memberRepository.findByMemid(memberId).orElse(null);
    }
    
    @Transactional
    public Member updateMember(Integer id, MemberUpdateForm form) {
        try {
            // 회원 아이디를 이용하여 회원 정보를 조회합니다.
            Optional<Member> optionalMember = memberRepository.findById(id);
            if (optionalMember.isPresent()) {
                // 회원 정보가 존재하는 경우에만 업데이트를 수행합니다.
                Member member = optionalMember.get();
                member.MemberUpdate(form); // MemberUpdateForm을 사용하여 회원 정보를 업데이트합니다.
                memberRepository.save(member); // 업데이트된 회원 정보를 저장합니다.
                return member; // 업데이트된 회원 정보를 반환합니다.
            } else {
                return null; // 회원을 찾을 수 없는 경우 null을 반환합니다.
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MemberService : updateMember() : " + e.getMessage());
            return null; // 다른 오류가 발생한 경우 null을 반환합니다.
        }
    }




    public Optional<Member> getMemberById(Integer id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public int deleteMember(Integer id) {
    	try {
            memberRepository.deleteById(id);
            return 1; // Successful deletion
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MemberService : deleteMember() : " + e.getMessage());
            return -1; // Error occurred during deletion
        }    
    }
    
    public Optional<Member> findMemberByMemid(String memid) {
        return memberRepository.findMemberByMemid(memid);
    }
    
    public List<Member> getMemberCount(){
    	return memberRepository.findMemberByCount();
    }
    
}
