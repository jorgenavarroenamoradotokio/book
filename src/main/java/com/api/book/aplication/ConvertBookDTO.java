package com.api.book.aplication;

import com.api.book.domain.Author;
import com.api.book.domain.Book;
import com.api.book.dto.BookDTO;

public class ConvertBookDTO {
	
	public BookDTO toBookDTO(Book book) {
		return BookDTO.builder()
				.id(book.getId())
				.title(book.getTitle())
				.genre(book.getGenre())
				.idAuthors(book.getAuthors().stream().map(Author::getId).toList())
				.build();
	}
}
