package com.api.book.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class SearchBookRequestDTO {

	String genre;
	int page;
	int pageSize;
}
