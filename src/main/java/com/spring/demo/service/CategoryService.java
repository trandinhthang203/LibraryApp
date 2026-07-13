package com.spring.demo.service;

import com.spring.demo.dto.request.CategoryRequest;
import com.spring.demo.entity.Category;
import java.util.List;

public interface CategoryService {
    Category create(CategoryRequest request);
    Category update(Long id, CategoryRequest request);
    void delete(Long id);
    Category getById(Long id);
    List<Category> getAll();
}