package com.spring.demo.dto.response;

import com.spring.demo.entity.User;
import com.spring.demo.enums.UserStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserResponse {
	  private Long id;
	  private String username;
	  private String email;
	  private String fullName;
	  private LocalDateTime createdAt;
	  private UserStatus status;
	  private RoleResponse role;
	  
	  public static UserResponse convertToResponse(User user) {
			return UserResponse.builder()
					.id(user.getId())
					.username(user.getUsername())
					.email(user.getEmail())
					.fullName(user.getFullName())
					.createdAt(user.getCreatedAt())
					.status(user.getStatus())
					.role(RoleResponse.builder()
							.id(user.getRole().getId())
							.name(user.getRole().getName())
							.description(user.getRole().getDescription())
							.build())
					.build();
		}
}