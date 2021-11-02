package com.kh.spring.springData;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring.book.Book;

public interface SpringDataRepository extends JpaRepository<Book, Long>{
	
	// 도서명 또는 작가로 도서 검색
	List<Book> findByTitleOrAuthor(String title, String author);
	
	// "카테고리가 문학이고, 도서 재고가 3권 이상이면서 도서명이 디로 시작하는 도서
	List<Book> findByCategoryAndBookAmtGreaterThanEqualAndTitleStartingWith(String category, Integer bookAmt, String title);
	
	int countByBookAmtGreaterThanEqual(int bookAmt);
	
	boolean existsByBookAmtGreaterThanEqual(int bookAmt);
	
	void deleteByTitle(String title);

}
