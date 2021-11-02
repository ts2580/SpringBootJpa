package com.kh.spring.rent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kh.spring.member.Member;

import lombok.Data;

@Data
@Entity
@DynamicUpdate 
@DynamicInsert
public class Rent {
	
	@Id
	@GeneratedValue
	private Long rmIdx;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private Member member; 
	// 객체지향적 관점에서 userId대신 Member 집어넣어
	// 다대일 관계. 멤버 하나가 여러 rent!
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // FetchType.EAGER : Lazy Initialization 설정 끔. QueryDslRepositoryImpl에서 양방향 맵핑으로 join할때 lazy로 바서 해보자
	@JoinColumn(name = "rmIdx")
	private List<RentBook> rentBooks = new ArrayList<RentBook>();
	// 나머지는 xxtoOne이니 참조되는 키가 하나
	// 근데 xxToMany는 참조되는 키가 여럿. 그럼 뭐를 참조로 잡냐. 뭘 외래키로 잡냐고
	// 그니까 중간에 맵핑해줄 테이블 하나 더 만들음. 
	// 아예 조인할때 사용할 외래키를 지정하면 테이블 하나 새로 안파도 됨
	// 양방향일때 mappedBy로 이어. PDF참조. 애가 연관관계의 주인임
	// RentBook에서 참조할께 rent니까 잡아줘
	// 단방향으로 바꿀꺼니 @OneToMany(mappedBy = "rent", cascade = CascadeType.ALL ) 날림
	// 그 동네에 있던 @JoinColumn(name = "rmIdx")은 가져와
	
	
	@Column(columnDefinition = "date default sysdate")
	private LocalDateTime regDate;
	
	@Column(columnDefinition = "number default 0")
	private Boolean isReturn;
	private String title;
	
	@Column(columnDefinition = "number default 0")
	private Integer rentBookCnt;
	
	public void changeRentBooks(List<RentBook> rentBooks) {
		this.rentBooks = rentBooks;
		for (RentBook rentBook : rentBooks) {
			// rentBook.setRent(this);
			// 양방향 맵핑할때 써. 각 클래스에서 주석 넣었던거 알아서 풀고
		}
	}
	
}
