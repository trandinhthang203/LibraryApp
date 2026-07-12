package com.spring.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.demo.dto.request.RegisterRequest;
import com.spring.demo.entity.User;
import com.spring.demo.enums.RoleName;
import com.spring.demo.repository.RoleRepository;
import com.spring.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
			throw new IllegalArgumentException("Username đã tồn tại");
		}
        
        if (userRepository.existsByEmail(request.getEmail())) {
        	throw new IllegalArgumentException("Email đã tồn tại");
        }
        
        Role role = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Role không tồn tại"));
        
        User user = User.Builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.fullName(request.getFullName())
				.role(role)
				.build();

        return userRepository.save(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user: " + username));
    }


}
