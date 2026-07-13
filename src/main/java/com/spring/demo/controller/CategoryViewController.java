package com.spring.demo.controller;

import com.spring.demo.dto.request.CategoryRequest;
import com.spring.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryViewController {

    private final CategoryService categoryService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "categories/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("category", new CategoryRequest());
        model.addAttribute("categoryId", null);
        return "categories/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("category") CategoryRequest request, Model model) {
        try {
            categoryService.create(request);
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "categories/form";
        }
        return "redirect:/categories";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var cat = categoryService.getById(id);
        CategoryRequest request = new CategoryRequest();
        request.setName(cat.getName());
        request.setDescription(cat.getDescription());
        model.addAttribute("category", request);
        model.addAttribute("categoryId", id);
        return "categories/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute("category") CategoryRequest request, Model model) {
        try {
            categoryService.update(id, request);
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "categories/form";
        }
        return "redirect:/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }
}