package com.spring.demo.service;

import com.spring.demo.dto.request.RegisterRequest;
import com.spring.demo.entity.User;

public interface UserService {
	User register(RegisterRequest registerRequest);
	User getByUsername(String username);
}
