package com.spring.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Getter @Setter
public class BookRequest {

    @NotBlank(message = "ISBN không được để trống")
    @Size(max = 20, message = "ISBN tối đa 20 ký tự")
    private String isbn;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255)
    private String title;

    @Size(max = 150)
    private String author;

    @Size(max = 150)
    private String publisher;

    private LocalDate publicationDate;

    private String summary;

    @NotNull(message = "Số lượng tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho phải >= 0")
    private Integer stockQuantity;

    private Set<Long> categoryIds;
}