package com.spring.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter
public class BorrowRequest {
    @NotNull(message = "Mã sách không được để trống")
    private Long bookId;

    @NotNull(message = "Mã người dùng không được để trống")
    private Long userId;

    private LocalDate expectedReturnDate;
}