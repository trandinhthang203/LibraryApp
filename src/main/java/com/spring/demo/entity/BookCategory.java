package com.spring.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class BookCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookCategoryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookId")
	@NotNull(message = "Phải gắn với một cuốn sách")
	private Book book;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId")
	@NotNull(message = "Phải gắn với một danh mục")
	private Category category;

    public BookCategory() {
    }

    public BookCategory(Book book, Category category) {
        this.book = book;
        this.category = category;
    }

    public Long getBookCategoryId() {
        return bookCategoryId;
    }

    public void setBookCategoryId(Long bookCategoryId) {
        this.bookCategoryId = bookCategoryId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}