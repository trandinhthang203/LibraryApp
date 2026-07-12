package com.spring.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter 
@Setter
public class RegisterRequest {
    @NotBlank
    @Size(min = 4, max = 50, message = "Username từ 4-50 ký tự")
    private String username;

    @NotBlank
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank
    @Size(min = 6, message = "Mật khẩu tối thiểu 6 ký tự")
    private String password;

    @NotBlank
    private String fullName;
}