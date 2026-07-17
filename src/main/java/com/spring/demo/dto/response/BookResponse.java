package com.spring.demo.dto.response;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BookResponse {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicationDate;
    private String summary;
    private Integer stockQuantity;
    private List<CategorySimple> categories;
}