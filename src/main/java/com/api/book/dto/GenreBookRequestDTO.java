package com.api.book.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class GenreBookRequestDTO {
	
	@NotBlank
	String genero;
}
