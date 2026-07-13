package com.spring.demo.controller;

import com.spring.demo.dto.request.BookRequest;
import com.spring.demo.dto.response.ApiResponse;
import com.spring.demo.dto.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spring.demo.dto.response.BookResponse;
import com.spring.demo.service.BookService;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookApiController {

    private final BookService bookService;

    // GET /api/books?keyword=java&page=0&size=10&sort=title,asc
    @GetMapping
    public ResponseEntity<ApiResponse<Object>> search(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        Page<BookResponse> page = bookService.search(keyword, pageable);
        return ResponseEntity.ok(ApiResponse.success("OK", PageResponse.from(page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("OK", bookService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> create(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo sách thành công", bookService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> update(@PathVariable Long id,
                                                        @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", bookService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa thành công", null));
    }
}