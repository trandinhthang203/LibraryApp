package com.spring.demo.controller;

import com.spring.demo.dto.request.CategoryRequest;
import com.spring.demo.dto.response.ApiResponse;
import com.spring.demo.entity.Category;
import com.spring.demo.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("OK", categoryService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("OK", categoryService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> create(@Valid @RequestBody CategoryRequest request) {
        Category created = categoryService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Tạo danh mục thành công", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> update(@PathVariable Long id,
                                                        @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", categoryService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa thành công", null));
    }
}