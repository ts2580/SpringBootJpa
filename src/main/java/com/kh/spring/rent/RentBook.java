package com.kh.spring.rent;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kh.spring.book.Book;

import lombok.Data;

@Data
@Entity
@DynamicUpdate 
@DynamicInsert
// @ToString(exclude = "rent") 양방향 맵핑서 오류날떄 찍기. rent에서 rentbook을, rentbook에서 rent로 Rent를 계속 불러서 에러.
public class RentBook {

	@Id
	@GeneratedValue
	private Long rbIdx;
	
	
	@ManyToOne
	@JoinColumn(name = "bkIdx")
	private Book book;
	// 책 하나에 대출 여러건
	
	// @ManyToOne
	// @JoinColumn(name = "rmIdx")
	// private Rent rent;
	// 안하는게 좋지만 양방향으로 한번 잡아봄
	// 첨부터 이렇게 할 일은 없겠지만
	// 기존 플젝을 JPA로 엎을때는 필요할수도
	// 단방향 맵핑으로 만들꺼니까 주석처리
	
	@Column(columnDefinition = "date default sysdate")
	private LocalDateTime regDate;
	private String state;
	
	@Column(columnDefinition = "date default sysdate+7")
	private LocalDateTime returnDate;
	
	@Column(columnDefinition = "number default 0")
	private Integer extensionCnt;
	
	public void changeRent(Rent rent) {
		//this.rent = rent;
		rent.getRentBooks().add(this);
		// 양방향 맵핑용 메서드
	}
	
	

}
