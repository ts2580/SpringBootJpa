package com.kh.spring.queryDsl;

import java.util.List;

import com.kh.spring.book.Book;
import com.kh.spring.member.Member;
import com.kh.spring.rent.Rent;
import com.kh.spring.rent.RentBook;
import com.querydsl.core.Tuple;

public interface QueryDslRepositoryCustom {
	// 여기에 쌩 sql 쿼리작성. TMfRJaus akfdlwl
	
	List<Rent> whereAnd(String title, String userId);
	
	List<Rent> whereOr(String title, String userId);
	
	List<RentBook> fetchJoin();
	
	List<Rent> projections(String userId);
	
	List<Tuple> tuple(String userId);
	
	List<RentBook> thetaJoin();
	
	List<Book> orderBy();
	
	List<Tuple> groupBy();
	
	List<Member> subQuery();
	
	List<Book> dynamicQueryWithBook(Book book);
	
	List<Member> dynamsicQueryWithMember(String keyword, String tell);
}
