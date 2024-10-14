package com.example.classicfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.classicfashion.model.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findUserByEmail(String Email);
    Optional<Users> findUserByUserName(String userName);
    Optional<Users> findUsersById(Long id);
    Boolean existsByEmail(String email);
}
