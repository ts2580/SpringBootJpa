package com.kh.spring.springData;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.kh.spring.book.Book;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class SpringDataTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private SpringDataRepository springDataRepository;
	
	@Test
	@DisplayName("모든 도서 검색 테스트")
	public void findAllBook() {
		springDataRepository.findAll().stream().forEach(e -> log.info(e.toString()));
	}
	
	@Test
	@DisplayName("도서번호로 도서조회")
	public void findById() {
		log.info(springDataRepository.findById(1001L).get().toString());
	}
	
	@Test
	@DisplayName("작가나 제목으로 조회")
	public void findByTitleOrAuthor() {
		springDataRepository.findByTitleOrAuthor("비행운", "황정은").forEach(e -> log.info(e.toString()));
	}
	
	@Test
	@DisplayName("카테고리가 문학이고, 도서 재고가 3권 이상이면서 도서명이 디로 시작하는 도서")
	public void findByCategoryAndOther() {
		springDataRepository.findByCategoryAndBookAmtGreaterThanEqualAndTitleStartingWith("문학", 3, "디").forEach(e -> log.info(e.toString()));
	}
	
	@Test
	@DisplayName("도서 등록")
	public void save() {
		Book book = new Book();
		book.setTitle("수용소 군도");
		book.setCategory("수필");
		book.setInfo("1234");
		book.setAuthor("솔레니친");
		book.setBookAmt(3);
		springDataRepository.save(book);
	}
	
	@Test
	@DisplayName("전체 도서 종류 조회")
	public void count() {
		log.info("전체 도서 권수 조회 : " + springDataRepository.count());
	}
	
	@Test
	@DisplayName("재고 2권 이상 도서 수량 조회")
	public void countByBookAmtGreaterThanEqual() {
		log.info("재고 2권 이상 도서 수량 조회 : " + springDataRepository.countByBookAmtGreaterThanEqual(2));
	}
	
	@Test
	@DisplayName("10권 이상 존재?")
	public void existBy() {
		log.info("재고 10권 이상 도서 수량 조회 : " + springDataRepository.existsByBookAmtGreaterThanEqual(10));
	}
	
	@Test
	@DisplayName("수용소군도 삭제")
	public void deleteBy() throws Exception{
		// springDataRepository.deleteByTitle("수용소 군도");
		// 삭제는 이걸로 안댐. MockMvc 가져오자
		
		mockMvc.perform(get("/delete-book").param("title", "수용소 군도")).andDo(print());

	}
	

	
	
	
	
	
	
	
	
}
