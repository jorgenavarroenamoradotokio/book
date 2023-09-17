package com.api.core.domain.dto;

import java.util.List;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class PageDTO<T> {

	private List<T> items;
	private int pageSize;
	private int page;
	private int total;
}
