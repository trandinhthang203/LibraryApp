package com.spring.demo.repository;

import com.spring.demo.entity.BorrowRecord;
import com.spring.demo.enums.BorrowStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    Page<BorrowRecord> findByUserId(Long userId, Pageable pageable);

    Page<BorrowRecord> findByStatus(BorrowStatus status, Pageable pageable);

    // Đếm số sách đang mượn của 1 cuốn để kiểm tra tồn kho
    @Query("""
           SELECT COUNT(br) FROM BorrowRecord br
           WHERE br.book.id = :bookId AND br.status = 'BORROWED'
           """)
    long countActiveBorrowsByBook(@Param("bookId") Long bookId);

    // Tìm các bản ghi quá hạn để cập nhật status OVERDUE (dùng cho scheduler nếu cần)
    @Query("""
           SELECT br FROM BorrowRecord br
           WHERE br.status = 'BORROWED' AND br.returnDate < :today
           """)
    List<BorrowRecord> findOverdueRecords(@Param("today") LocalDate today);
}