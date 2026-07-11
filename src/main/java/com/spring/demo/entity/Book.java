package com.spring.demo.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;

	@NotBlank(message = "Tiêu đề sách không được để trống")
	@Size(max = 255, message = "Tiêu đề không vượt quá 255 ký tự")
	private String title;

	@NotBlank(message = "Tác giả không được để trống")
	@Size(max = 150)
	private String author;

	@Size(max = 150)
	private String publisher;

	@Past(message = "Ngày xuất bản phải là ngày trong quá khứ")
	private Date publicationDate;

	@Size(max = 2000, message = "Tóm tắt không vượt quá 2000 ký tự")
	private String summary;

	@NotNull(message = "Số lượng tồn kho không được để trống")
	@Min(value = 0, message = "Số lượng tồn kho không được âm")
	private Integer stockQuantity;
	private LocalDateTime createAt;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
	private List<BorrowRecord> borrowRecords;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
	private List<BookCategory> bookCategories;
	
	public Book() {
		
	}


	public Book(Long bookId, String title, String author, String publisher, Date publicationDate, String summary,
			Integer stockQuantity, LocalDateTime createAt) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.summary = summary;
		this.stockQuantity = stockQuantity;
		this.createAt = createAt;
	}


	public Long getBookId() {
		return bookId;
	}


	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}


	public Date getPublicationDate() {
		return publicationDate;
	}


	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public Integer getStockQuantity() {
		return stockQuantity;
	}


	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}


	public LocalDateTime getCreateAt() {
		return createAt;
	}


	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}
	
	
}
