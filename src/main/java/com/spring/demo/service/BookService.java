package com.spring.demo.service;

import com.spring.demo.dto.request.BookRequest;
import com.spring.demo.dto.response.BookResponse;
import com.spring.demo.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse create(BookRequest request);
    BookResponse update(Long id, BookRequest request);
    void delete(Long id);
    BookResponse getById(Long id);
    Page<BookResponse> search(String keyword, Pageable pageable);
    Book getEntityById(Long id);
}