package com.api.book.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Author {

	private int id;
	private String name;
}
