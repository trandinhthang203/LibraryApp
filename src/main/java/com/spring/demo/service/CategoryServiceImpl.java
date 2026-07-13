package com.spring.demo.service;


import com.spring.demo.dto.request.CategoryRequest;
import com.spring.demo.entity.Category;
import com.spring.demo.exception.BusinessException;
import com.spring.demo.exception.ResourceNotFoundException;
import com.spring.demo.repository.CategoryRepository;
import com.spring.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(CategoryRequest request) {
        categoryRepository.findByNameIgnoreCase(request.getName()).ifPresent(c -> {
            throw new BusinessException("Danh mục đã tồn tại");
        });
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, CategoryRequest request) {
        Category category = getById(id);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        Category category = getById(id);
        categoryRepository.delete(category);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục id=" + id));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}