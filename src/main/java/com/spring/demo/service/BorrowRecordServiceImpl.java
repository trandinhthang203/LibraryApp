package com.spring.demo.service;


import com.spring.demo.dto.request.BorrowRequest;
import com.spring.demo.entity.*;
import com.spring.demo.enums.BorrowStatus;
import com.spring.demo.exception.BusinessException;
import com.spring.demo.exception.ResourceNotFoundException;
import com.spring.demo.repository.*;
import com.spring.demo.service.BorrowRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BorrowRecordServiceImpl implements BorrowRecordService {

    private static final int DEFAULT_BORROW_DAYS = 14;
    private static final BigDecimal FINE_PER_DAY = new BigDecimal("5000"); // VND/ngày trễ

    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BorrowRecord borrowBook(BorrowRequest request) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sách"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));

        long activeBorrows = borrowRecordRepository.countActiveBorrowsByBook(book.getId());
        if (activeBorrows >= book.getStockQuantity()) {
            throw new BusinessException("Sách hiện đã hết, không thể mượn thêm");
        }

        LocalDate expected = request.getExpectedReturnDate() != null
                ? request.getExpectedReturnDate()
                : LocalDate.now().plusDays(DEFAULT_BORROW_DAYS);

        BorrowRecord record = BorrowRecord.builder()
                .user(user)
                .book(book)
                .borrowDate(LocalDate.now())
                .returnDate(expected)
                .status(BorrowStatus.BORROWED)
                .fineAmount(BigDecimal.ZERO)
                .build();

        return borrowRecordRepository.save(record);
    }

    @Override
    @Transactional
    public BorrowRecord returnBook(Long borrowRecordId) {
        BorrowRecord record = borrowRecordRepository.findById(borrowRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiếu mượn"));

        if (record.getStatus() == BorrowStatus.RETURNED) {
            throw new BusinessException("Sách này đã được trả trước đó");
        }

        LocalDate today = LocalDate.now();
        record.setActualReturnDate(today);

        if (today.isAfter(record.getReturnDate())) {
            long lateDays = ChronoUnit.DAYS.between(record.getReturnDate(), today);
            record.setFineAmount(FINE_PER_DAY.multiply(BigDecimal.valueOf(lateDays)));
        }
        record.setStatus(BorrowStatus.RETURNED);

        return borrowRecordRepository.save(record);
    }

    @Override
    public Page<BorrowRecord> getByUser(Long userId, Pageable pageable) {
        return borrowRecordRepository.findByUserId(userId, pageable);
    }

    @Override
    public Page<BorrowRecord> getAll(Pageable pageable) {
        return borrowRecordRepository.findAll(pageable);
    }
}