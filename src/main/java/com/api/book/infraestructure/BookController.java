package com.api.book.infraestructure;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.book.aplication.BookService;
import com.api.book.dto.BookDTO;
import com.api.book.dto.BookRequestDTO;
import com.api.book.dto.SearchBookRequestDTO;
import com.api.core.domain.dto.PageDTO;
import com.api.core.exception.NotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/V1")
@RequiredArgsConstructor
@Validated
public class BookController {
	
	private final BookService service;

	@GetMapping("/books")
	public ResponseEntity<PageDTO<BookDTO>> searchBook(
			@Valid @RequestParam(value = "genre", required = false) String genre,
			@Valid @RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@Valid @Min(1) @Max(100) @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize) {

		PageDTO<BookDTO> pageDTO = service
				.searchBook(SearchBookRequestDTO.builder().genre(genre).page(page).pageSize(pageSize).build());

		return ResponseEntity.ok(pageDTO);
	}
	
	@GetMapping("/books/{bookId}")
	public ResponseEntity<BookDTO> getBookById(@PathVariable("bookId") int bookId) {
		BookDTO bookDTO = service.getBookByID(bookId);
		return ResponseEntity.ok(bookDTO);
	}

	@GetMapping("/error")
	public ResponseEntity<Error> error(){
		throw new NotFoundException("Error");
	}
	
	@PostMapping("/books")
	public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) {
		BookDTO bookDTO = service.createBook(bookRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO);
	}
	

}
