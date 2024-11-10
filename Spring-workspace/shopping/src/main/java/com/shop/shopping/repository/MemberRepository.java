package com.shop.shopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.shopping.model.Member;
import com.shop.shopping.model.Product;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByMemid(String memid);
    Optional<Member> findByMemidAndMempwd(String memid, String mempwd);
    Optional<Member> findMemberByMemid(String memid);
    
    @Query("SELECT m FROM Member m WHERE m.admintype = false")
    List<Member> findMemberByCount();
}
