package com.spring.demo.config;

import com.spring.demo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider((UserDetailsService) userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider authProvider) throws Exception {
        http
            .authenticationProvider(authProvider)
            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers("/", "/home", "/login", "/register", "/css/**", "/js/**").permitAll()
            	    .requestMatchers("/api/auth/**").permitAll()

            	    // Web: xem sách công khai (GET), nhưng thêm/sửa/xóa/new/edit/delete chỉ ADMIN
            	    .requestMatchers(HttpMethod.GET, "/books", "/books/{id}").permitAll()
            	    .requestMatchers("/books/new", "/books/*/edit", "/books/*/delete").hasRole("ADMIN")
            	    .requestMatchers("/books/*/borrow").hasAnyRole("USER", "ADMIN")

            	    // Web: category chỉ ADMIN toàn bộ
            	    .requestMatchers("/categories/**").hasRole("ADMIN")

            	    // API giữ nguyên như cũ
            	    .requestMatchers(HttpMethod.GET, "/api/books/**", "/api/categories/**").permitAll()
            	    .requestMatchers(HttpMethod.POST, "/api/books/**", "/api/categories/**").hasRole("ADMIN")
            	    .requestMatchers(HttpMethod.PUT, "/api/books/**", "/api/categories/**").hasRole("ADMIN")
            	    .requestMatchers(HttpMethod.DELETE, "/api/books/**", "/api/categories/**").hasRole("ADMIN")
            	    .requestMatchers("/api/borrow/**").hasAnyRole("USER", "ADMIN")

            	    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));

        return http.build();
    }

}
