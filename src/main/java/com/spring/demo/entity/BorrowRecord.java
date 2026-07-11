package com.spring.demo.entity;

import java.text.DecimalFormat;
import java.util.Date;

import jakarta.persistence.*;

@Entity
public class BorrowRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String borrowId;
	
	private Date borrowDate;
	private Date returnDate;
	private Date actualReturnDate;
	private String status;
	private DecimalFormat fineAmount;
	private String text;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookId")
	private Book book;


	public BorrowRecord() {
		
	}

	public BorrowRecord(String borrowId, Date borrowDate, Date returnDate, Date actualReturnDate, String status,
			DecimalFormat fineAmount, String text, User user, Book book) {
		
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


	public String getBorrowId() {
		return borrowId;
	}


	public void setBorrowId(String borrowId) {
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


	public DecimalFormat getFineAmount() {
		return fineAmount;
	}


	public void setFineAmount(DecimalFormat fineAmount) {
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
