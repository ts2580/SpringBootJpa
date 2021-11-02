package com.kh.spring.queryDsl;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.member.Member;

public interface QueryDslRepository extends JpaRepository<Member, String>, QueryDslRepositoryCustom{
	// 여기에 프록시객체
	
	
}
