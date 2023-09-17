package com.api.book.dto;

import java.util.List;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class BookDTO {

	private int id;
	private String title;
	private String genre;
	private List<Integer> idAuthors;
	
}
