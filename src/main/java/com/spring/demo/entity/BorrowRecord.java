package com.spring.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class BorrowRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long borrowId;

	@NotNull(message = "Ngày mượn không được để trống")
	@PastOrPresent(message = "Ngày mượn không được là ngày trong tương lai")
	private Date borrowDate;

	@NotNull(message = "Ngày hẹn trả không được để trống")
	private Date returnDate;

	private Date actualReturnDate;

	@Pattern(regexp = "BORROWED|RETURNED|OVERDUE", message = "Trạng thái không hợp lệ")
	private String status;

	@DecimalMin(value = "0.0", message = "Tiền phạt không được âm")
	private BigDecimal fineAmount;

	@Size(max = 500)
	private String text;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookId")
	private Book book;


	public BorrowRecord() {
		
	}

	public BorrowRecord(Long borrowId, Date borrowDate, Date returnDate, Date actualReturnDate, String status,
			BigDecimal fineAmount, String text, User user, Book book) {
		
		this.borrowId = borrowId;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
		this.actualReturnDate = actualReturnDate;
		this.status = status;
		this.fineAmount = fineAmount;
		this.text = text;
		this.user = user;
		this.book = book;
	}


	public Long getBorrowId() {
		return borrowId;
	}


	public void setBorrowId(Long borrowId) {
		this.borrowId = borrowId;
	}


	public Date getBorrowDate() {
		return borrowDate;
	}


	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}


	public Date getReturnDate() {
		return returnDate;
	}


	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}


	public Date getActualReturnDate() {
		return actualReturnDate;
	}


	public void setActualReturnDate(Date actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public BigDecimal getFineAmount() {
		return fineAmount;
	}


	public void setFineAmount(BigDecimal fineAmount) {
		this.fineAmount = fineAmount;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Book getBook() {
		return book;
	}


	public void setBook(Book book) {
		this.book = book;
	}
	
	
}
