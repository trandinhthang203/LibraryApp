package com.spring.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.demo.dto.request.RegisterRequest;
import com.spring.demo.dto.response.*;
import com.spring.demo.entity.User;
import com.spring.demo.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {
	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
		try {
			User registeredUser = userService.register(request);
			UserResponse userResponse = UserResponse.convertToResponse(registeredUser);
			return ResponseEntity.ok(ApiResponse.success("Đăng ký thành công", userResponse));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(ApiResponse.error("Đăng ký thất bại: " + e.getMessage()));
		}
	}
	
}
