package com.spring.demo.controller;

import com.spring.demo.dto.request.BorrowRequest;
import com.spring.demo.dto.response.ApiResponse;
import com.spring.demo.dto.response.PageResponse;
import com.spring.demo.entity.BorrowRecord;
import com.spring.demo.service.BorrowRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor
public class BorrowApiController {

    private final BorrowRecordService borrowRecordService;

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> borrow(@Valid @RequestBody BorrowRequest request) {
        BorrowRecord record = borrowRecordService.borrowBook(request);
        return ResponseEntity.ok(ApiResponse.success("Mượn sách thành công", record));
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<ApiResponse<Object>> returnBook(@PathVariable Long id) {
        BorrowRecord record = borrowRecordService.returnBook(id);
        return ResponseEntity.ok(ApiResponse.success("Trả sách thành công", record));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Object>> getByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 10, sort = "borrowDate") Pageable pageable) {

        Page<BorrowRecord> page = borrowRecordService.getByUser(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success("OK", PageResponse.from(page)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAll(
            @PageableDefault(size = 10, sort = "borrowDate") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("OK", PageResponse.from(borrowRecordService.getAll(pageable))));
    }
}