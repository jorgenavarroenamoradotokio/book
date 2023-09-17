package com.api.book.domain;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {

	private Integer id;
	private String title;
	private String genre;
	private List<Author> authors;
}
