package com.spring.demo.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.demo.dto.request.RegisterRequest;
import com.spring.demo.entity.Role;
import com.spring.demo.entity.User;
import com.spring.demo.enums.RoleName;
import com.spring.demo.enums.UserStatus;
import com.spring.demo.exception.ResourceNotFoundException;
import com.spring.demo.repository.RoleRepository;
import com.spring.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

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
        
        User user = User.builder()
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user: " + username));
		
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole().getName().name())))
                .disabled(user.getStatus() == UserStatus.INACTIVE)
                .build();
	}


}
