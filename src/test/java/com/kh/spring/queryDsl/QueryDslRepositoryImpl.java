package com.kh.spring.queryDsl;

import java.util.List;

import javax.persistence.EntityManager;

import com.kh.spring.book.Book;
import com.kh.spring.book.QBook;
import com.kh.spring.member.Member;
import com.kh.spring.member.QMember;
import com.kh.spring.rent.QRent;
import com.kh.spring.rent.QRentBook;
import com.kh.spring.rent.Rent;
import com.kh.spring.rent.RentBook;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class QueryDslRepositoryImpl implements QueryDslRepositoryCustom{
	// 반드시 QueryDslRepository + impl형태로 인터페이스명 작성. 인터페이스의 구현체
	
	private final JPAQueryFactory query;
	private QRent rent = QRent.rent; 
	private QRentBook rentBook = QRentBook.rentBook;
	private QMember member = QMember.member;
	private QBook book = QBook.book;
	
	public QueryDslRepositoryImpl(EntityManager em) {
		this.query = new JPAQueryFactory(em);
	}
	
	public List<Rent> whereAnd(String title, String userId){
		
		// where 메서드에 여러개의 Predicate를 전달할 수 있다. 이는 and연산과 동일하다.
		List<Rent> rents = query.select(rent).from(rent).where(rent.title.startsWith(title),rent.member.userId.eq(userId)).fetch();
		
		return rents;
	}

	@Override
	public List<Rent> whereOr(String title, String userId) {

		List<Rent> rents = query.select(rent).from(rent).where(rent.title.startsWith(title).or(rent.member.userId.eq(userId))).fetch();
		
		return rents;
	}

	@Override
	public List<RentBook> fetchJoin() {

		// List<RentBook> rentBooks = query.select(rentBook)
		//									.from(rentBook)
		//									.innerJoin(rentBook.book).fetchJoin()
		//									.innerJoin(rentBook.rent).fetchJoin()
		//									.where(rentBook.regDate.eq(rentBook.book.regDate)).fetch();
		// 양방향 맵핑할때 시험해보자
		List<RentBook> rentBooks = query.select(rentBook).from(rentBook).where(rentBook.regDate.eq(rentBook.book.regDate)).fetch();
		return rentBooks;
	}

	@Override
	public List<Rent> projections(String userId) {

		List<Rent> rents = query.select(Projections.fields(Rent.class, rent.title, rent.rmIdx)).from(rent).where(rent.member.userId.eq(userId)).fetch();
		
		return rents;
	}

	@Override
	public List<Tuple> tuple(String userId) {
		
		List<Tuple> tuple = query.select(rent.title, rent.rmIdx).from(rent).where(rent.member.userId.eq(userId)).fetch();
		return tuple;
	}

	@Override
	public List<RentBook> thetaJoin() {
		
		// List<RentBook> rentBooks = query.select(rentBook).from(rentBook, member).innerJoin(rentBook.rent, rent).fetchJoin().innerJoin(rentBook.book).fetchJoin().innerJoin(rent.member).fetchJoin().where(rentBook.regDate.eq(rent.member.regDate)).fetch();
		// 양방향 맵핑할 때 실험해보장. 쿼리 하나로 돌고 깔끔함
		List<RentBook> rentBooks = query.select(rentBook).distinct().from(rentBook, member).where(rentBook.regDate.eq(member.regDate)).fetch();
		return rentBooks;
	}

	@Override
	public List<Book> orderBy() {

		List<Book> Book = query.select(book).distinct().from(book).orderBy(book.bookAmt.desc()).limit(2).fetch();
		return Book;
	}

	@Override
	public List<Tuple> groupBy() {
		// Entity가 아니라(맥스, 평균) Expression Type이 들어감
		// 튜플-리절트셋의 한줄. Entity가 아님.
		
		List<Tuple> tuple = query.select(book.category, book.bookAmt.max(), book.bookAmt.avg(), book.rentCnt.avg()).from(book).groupBy(book.category).fetch();
		return tuple;
	}

	@Override
	public List<Member> subQuery() {
		
		List<Member> members = query.select(member).from(member).where(member.userId.in(
																JPAExpressions.select(rent.member.userId)
																				.from(rent)
																				.innerJoin(rent.rentBooks, rentBook)
																				.where(rentBook.state.eq("대출"))
																				)
																		).fetch();
		
		return members;
	}

	@Override
	public List<Book> dynamicQueryWithBook(Book b) {
		// 조건 좀 복잡하면 메소드로 뺴주고 표현식 넣어주자
		
		List<Book> Books = query.select(book).from(book).where(bookAmtGoe(b.getBookAmt()), rentCntLoe(b.getRentCnt())).fetch();
		return Books;
	}
	
	@Override
	public List<Member> dynamsicQueryWithMember(String keyword, String tell) {

		List<Member> members = query.select(member).from(member).where(emailOruserIdEq(keyword), member.tell.eq(tell)).fetch();
		return members;
	}
	
	public BooleanExpression bookAmtGoe(int bookAmt) {
		return bookAmt == 0 ? null // 매개변수로 0 넘어오면 무시하고 다음 조건절부터 쿼리 작성
							: book.bookAmt.goe(bookAmt);
	}
	
	public BooleanExpression rentCntLoe(int rentCnt) {
		return rentCnt == 0 ? null // 매개변수로 0 넘어오면 무시하고 다음 조건절부터 쿼리 작성
							: book.rentCnt.loe(rentCnt);
	}
	
	public BooleanExpression emailOruserIdEq(String keyword) {
		return keyword.isBlank() ? null // 하나라도 null이면 오류남. 아예 메소드로 or절을 만들어서 묶자
							: member.userId.eq(keyword).or(member.email.eq(keyword));
	}

	
}
