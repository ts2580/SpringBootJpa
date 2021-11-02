package com.kh.spring.book;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


import lombok.Data;

@Data
@Entity
@DynamicUpdate 
@DynamicInsert
public class Book {
	
	@Id
	@GeneratedValue // JPA정책에 따라 식별자 자동 생성
	private Long bkIdx;
	private String isbn;
	private String category;
	private String title;
	private String author;
	private String info;
	
	@Column(columnDefinition = "number default 1")
	private Integer bookAmt;
	
	@Column(columnDefinition = "date default sysdate")
	private LocalDateTime regDate;
	
	@Column(columnDefinition = "number default 0")
	private Integer rentCnt;
	
	
	
}
