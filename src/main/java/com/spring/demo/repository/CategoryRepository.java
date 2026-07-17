package com.spring.demo.repository;

import com.spring.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByNameIgnoreCase(String name);    
}
