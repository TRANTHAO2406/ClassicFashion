package com.example.classicfashion.security;

import com.example.classicfashion.model.Users;
import com.example.classicfashion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        System.out.println("Người dùng tìm thấy: " + user.getEmail() + ", Trạng thái: " + user.getStatus());  // In ra người dùng đã tìm thấy
        return new CustomUserDetails(user);
    }

}
