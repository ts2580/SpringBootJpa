package com.kh.spring.book;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookController {
	
	private final BookService bookService;
	
	@GetMapping("delete-book")
	public String deleteBook(String title) {
		bookService.deleteByTitle(title);
		return "index";
	}
}
