package com.api.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class BookRequestDTO {

	@NotBlank
	private String title;
	
	@NotBlank
	private String genre;
	
	@Positive
	@NotNull
	private Integer idAuthor;
	
	
}
