package com.spring.demo.service;

import com.spring.demo.dto.request.BorrowRequest;
import com.spring.demo.entity.BorrowRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BorrowRecordService {
    BorrowRecord borrowBook(BorrowRequest request);
    BorrowRecord returnBook(Long borrowRecordId);
    Page<BorrowRecord> getByUser(Long userId, Pageable pageable);
    Page<BorrowRecord> getAll(Pageable pageable);
}