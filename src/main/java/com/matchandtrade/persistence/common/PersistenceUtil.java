package com.matchandtrade.persistence.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PersistenceUtil {

	// Utility classes should not have public constructors
	private PersistenceUtil() { }	
	
	public static Pageable buildPageable(Pagination pagination) {
		return buildPageable(pagination.getNumber(), pagination.getSize());
	}
	
	public static Pageable buildPageable(Integer pageNumber, Integer pageSize) {
		int page = 1;
		int size = 10;
		if (pageNumber != null) {
			page = pageNumber;
		}
		if (pageSize != null) {
			size = pageSize;
		}
		// Pageable pages start at zero
		return new PageRequest(page-1, size);
	}

	public static <T> SearchResult<T> buildSearchResult(Pageable pageable, Page<T> page) {
		Pagination pagination = new Pagination(pageable.getPageNumber(), pageable.getPageSize(), page.getTotalElements());
		return new SearchResult<>(page.getContent(), pagination);
	}

}
