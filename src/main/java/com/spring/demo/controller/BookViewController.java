package com.spring.demo.controller;

import com.spring.demo.dto.request.BookRequest;
import com.spring.demo.dto.request.BorrowRequest;
import com.spring.demo.entity.User;
import com.spring.demo.service.BookService;
import com.spring.demo.repository.CategoryRepository;
import com.spring.demo.service.BorrowRecordService;
import com.spring.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookViewController {

    private final BookService bookService;
    private final BorrowRecordService borrowRecordService;
    private final UserService userService;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public String list(@RequestParam(required = false) String keyword,
                        @PageableDefault(size = 2) Pageable pageable,
                        Model model) {
        model.addAttribute("books", bookService.search(keyword, pageable));
        model.addAttribute("keyword", keyword);
        return "books/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getById(id));
        return "books/detail";
    }

    // ---- ADMIN: thêm sách ----
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("book", new BookRequest());
        model.addAttribute("bookId", null);
        model.addAttribute("categories", categoryRepository.findAll());
        return "books/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("book") BookRequest request, Model model) {
        try {
            bookService.create(request);
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("categories", categoryRepository.findAll());
            return "books/form";
        }
        return "redirect:/books";
    }

    // ---- ADMIN: sửa sách ----
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var book = bookService.getById(id);
        BookRequest request = new BookRequest();
        request.setIsbn(book.getIsbn());
        request.setTitle(book.getTitle());
        request.setAuthor(book.getAuthor());
        request.setPublisher(book.getPublisher());
        request.setPublicationDate(book.getPublicationDate());
        request.setSummary(book.getSummary());
        request.setStockQuantity(book.getStockQuantity());
        // set selected category ids for the edit form
        if (book.getCategories() != null) {
            request.setCategoryIds(book.getCategories().stream().map(c -> c.getId()).collect(java.util.stream.Collectors.toSet()));
        }

        model.addAttribute("book", request);
        model.addAttribute("bookId", id); // dùng riêng để form biết action nào
        model.addAttribute("categories", categoryRepository.findAll());
        return "books/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute("book") BookRequest request, Model model) {
        try {
            bookService.update(id, request);
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("bookId", id);
            return "books/form";
        }
        return "redirect:/books";
    }

    // ---- ADMIN: xóa sách ----
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    // ---- USER: mượn sách ----
    @PostMapping("/{id}/borrow")
    public String borrow(@PathVariable Long id, Authentication authentication) {
        User currentUser = userService.getByUsername(authentication.getName());
        BorrowRequest request = new BorrowRequest();
        request.setBookId(id);
        request.setUserId(currentUser.getId());
        borrowRecordService.borrowBook(request);
        return "redirect:/books";
    }
}