package com.spring.demo.config;

import com.spring.demo.entity.*;
import com.spring.demo.enums.RoleName;
import com.spring.demo.enums.UserStatus;
import com.spring.demo.repository.RoleRepository;
import com.spring.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(
                        Role.builder().name(RoleName.ROLE_ADMIN).description("Quản trị viên").build()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseGet(() -> roleRepository.save(
                        Role.builder().name(RoleName.ROLE_USER).description("Người dùng").build()));

        if (!userRepository.existsByUsername("admin")) {
            userRepository.save(User.builder()
                    .username("admin")
                    .email("admin@lms.com")
                    .password(passwordEncoder.encode("admin123")) 
                    .fullName("System Admin")
                    .status(UserStatus.ACTIVE)
                    .role(adminRole)
                    .build());
        }
        
        if (!userRepository.existsByUsername("user")) {
            userRepository.save(User.builder()
                    .username("user")
                    .email("user@lms.com")
                    .password(passwordEncoder.encode("user123"))
                    .fullName("Test User")
                    .status(UserStatus.ACTIVE)
                    .role(userRole)
                    .build());
        }
    }
}