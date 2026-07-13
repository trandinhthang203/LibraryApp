package com.spring.demo.repository;

import com.spring.demo.entity.BookCategory;
import com.spring.demo.entity.BookCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookCategoryRepository extends JpaRepository<BookCategory, BookCategoryId> {
    List<BookCategory> findByBookId(Long bookId);
    void deleteByBookId(Long bookId);
}
