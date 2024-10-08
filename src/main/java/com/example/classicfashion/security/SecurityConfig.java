//package com.example.classicfashion.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Vô hiệu hóa bảo mật CSRF
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll() // cho phép tất cả các request mà không cần xác thực
//                );
//
//        return http.build();
//    }
//}
