package com.example.classicfashion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.classicfashion.model.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findUserByEmail(String Email);
    Optional<Users> findUserByUserName(String userName);
    Optional<Users> findUsersById(Long id);
    @Query("SELECT u FROM Users u WHERE LOWER(u.userName) LIKE  LOWER(CONCAT('%', :keyword, '%'))")
    Page<Users> searchUsersByUserName(@Param("keyword") String keyword, Pageable pageable);
}
