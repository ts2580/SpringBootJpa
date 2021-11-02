package com.kh.spring.queryDsl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.spring.book.Book;
import com.kh.spring.member.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class QueryTest {
	
	@Autowired
	private QueryDslRepository queryDslRepository;
	
	@Test
	@DisplayName("대출건 제목이 디디 and 대출자 아이디가 test")
	public void whereAnd() {
		queryDslRepository.whereAnd("디디", "test").forEach(e -> log.info(e.toString()));
	}
	
	@Test
	@DisplayName("대출건 제목이 디디 or 대출자 아이디가 jpa")
	public void whereOr() {
		queryDslRepository.whereOr("디디", "jpa").forEach(e -> log.info(e.toString()));
	}
	
	@Test
	@DisplayName("대출등록일=도서등록일")
	public void fetchJoin() {
		queryDslRepository.fetchJoin().forEach(e -> log.info(e.toString()));
	}
	
	@Test
	@DisplayName("대출자 아이디 test인 모든 대출건의 대출건 제목과 대출번호")
	public void projections() {
		queryDslRepository.projections("test").forEach(e -> log.info(e.toString()));
	}
	
	@Test
	@DisplayName("대출자 아이디 test인 모든 대출건의 대출건 제목과 대출번호")
	public void innerJoinProjection() {
		queryDslRepository.tuple("test").forEach(e -> log.info(e.get(1, String.class)));
	}
	
	@Test
	@DisplayName("대출일자와 가입일자가 같은 회원이 존재하는 대출도서")
	public void thetaJoin() {
		// Member와 Rent는 원래 Join이 안됨. 근데 Qdsl은 걍 묶어줌
		queryDslRepository.thetaJoin().forEach(e -> log.info(e.toString()));
	}
	
	@Test
	@DisplayName("도서재고기준 탑2")
	public void orderBy() {
		queryDslRepository.orderBy().forEach(e -> log.info(e.toString()));
	}
	
	@Test
	@DisplayName("카테고리병 도서들의 최대재고, 평균재고, 평균대출횟수")
	public void groupBy() {
		queryDslRepository.groupBy().forEach(e -> log.info(e.toString()));
	}
	
	@Test
	@DisplayName("대출 도서의 상태가 '대출'인 대출도서가 한권이라도 존재하는 회원을 조회")
	public void subQuery() {
		queryDslRepository.subQuery().forEach(e -> log.info(e.toString()));
	}
	
	@Test
	@DisplayName("동적쿼리")
	public void dynamicQueryWithBook() {
		// 도서 재고가 매개변수로 전달받은 값보다 크거나 같으면서
		// 도서대출 횟수가 매개변수로 전발받은 값보다 작거가 같은 도서를 조회
		// 만약 도서재고나 대출횟수가 0으로 전달되면 조건에서 제외
		
		Book book = new Book();
		book.setBookAmt(2);
		book.setRentCnt(0);
		queryDslRepository.dynamicQueryWithBook(book).forEach(e -> log.info(e.toString()));
	}
	
	@Test
	@DisplayName("or 동적쿼리")
	public void dynamsicQueryWithMember() {
		// 사용자가 입력한 키워드가 이메일 또는 아이디 이면서
		// 전화번호가 tell과 같은 회원을 조회
		
		String keyword = "test";
		String tell = "010-0112-0122";
		
		queryDslRepository.dynamsicQueryWithMember(keyword, tell).forEach(e -> log.info(e.toString()));
	}
	
	
	
	
	
	
	
}
