package com.api.book.aplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.api.book.domain.Author;
import com.api.book.domain.Book;
import com.api.book.dto.BookDTO;
import com.api.book.dto.BookRequestDTO;
import com.api.book.dto.SearchBookRequestDTO;
import com.api.core.domain.dto.PageDTO;
import com.api.core.exception.NotFoundException;
import com.github.javafaker.Faker;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	private List<Book> books;
	private List<Author> authors;
	
	private ConvertBookDTO convertBook = new ConvertBookDTO();
	
	@PostConstruct
	public void postConstructor() {
		Faker faker = new Faker();
		authors = new ArrayList<>(IntStream.range(0, 10)
				.mapToObj(i -> Author.builder().id(i)
						.name(faker.book().author()).build()).toList());
		
		books = new ArrayList<>(IntStream.range(0, 101)
				.mapToObj(i -> Book.builder().id(i)
						.title(faker.book().title())
						.genre(faker.book().genre())
						.authors(List.of(authors.get((int) (Math.random() *10))))
						.build()).toList());
	}
	
	public BookDTO getBookByID(int idBook) {
		Book book = searchBookByID(idBook);
		return convertBook.toBookDTO(book);
	}
	
	public BookDTO createBook(BookRequestDTO bookRequestDTO) {
		Book libro = Book.builder()
				.id(nextLibroId())
				.title(bookRequestDTO.getTitle())
				.genre(bookRequestDTO.getGenre())
				.authors(List.of(searchAuthorByID(bookRequestDTO.getIdAuthor())))
				.build();
		
		books.add(libro);
		
		return convertBook.toBookDTO(libro);
	}
	
	public void deleteBookByID(int idBook) {
		Optional<Book> libro = books.stream()
				.filter(a -> a.getId() == idBook)
				.findFirst();
		libro.ifPresent(books::remove);
	}
	
	public BookDTO updateBook(int idBook, BookRequestDTO bookRequestDTO) {
		Book book = searchBookByID(idBook);
		book.setTitle(bookRequestDTO.getTitle());
		book.setGenre(bookRequestDTO.getGenre());
		book.setAuthors(List.of(searchAuthorByID(bookRequestDTO.getIdAuthor())));
		return convertBook.toBookDTO(book);
	}
	
	public BookDTO updateGenre(int idBook, BookRequestDTO bookRequestDTO) {
		Book book = searchBookByID(idBook);
		book.setGenre(bookRequestDTO.getGenre());
		return convertBook.toBookDTO(book);
	}
	
	public PageDTO<BookDTO> searchBook(SearchBookRequestDTO searchBookDTO){
		List<Book> filterBooks = 
				Optional.ofNullable(StringUtils.trimToNull(searchBookDTO.getGenre()))
				.map(String::toLowerCase)
				.map(
						genero ->
							books.stream()
								.filter(book -> book.getGenre().toLowerCase().contains(genero))
								.toList())
				.orElse(books); 
		
		
		int start = searchBookDTO.getPage() * searchBookDTO.getPageSize();
		
		if(start >= filterBooks.size()) {
			return PageDTO.<BookDTO>builder()
					.items(List.of())
					.page(searchBookDTO.getPage())
					.pageSize(searchBookDTO.getPageSize())
					.total(filterBooks.size())
					.build();
		}
		
		int end = Math.min(start + searchBookDTO.getPageSize(), filterBooks.size());
		
		List<BookDTO> items = IntStream.range(start, end).mapToObj(filterBooks::get).map(convertBook::toBookDTO).toList();
		
		return PageDTO.<BookDTO>builder()
				.page(searchBookDTO.getPage())
				.pageSize(searchBookDTO.getPageSize())
				.total(filterBooks.size())
				.items(items)
				.build();
	}

	private Book searchBookByID(int idBook) {
		return books.stream()
				.filter(a -> a.getId() == idBook)
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Libro id:%s no encontrado".formatted(idBook)));
	}
	
	private Author searchAuthorByID(int idAuthor) {
		return authors.stream()
				.filter(a -> a.getId() == idAuthor)
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Autor id:%s no encontrado".formatted(idAuthor)));
	}
	
	private synchronized int nextLibroId() {
		return books.stream().map(Book::getId).reduce(Math::max).map(id -> id + 1).orElse(-1);
	}
	
}
