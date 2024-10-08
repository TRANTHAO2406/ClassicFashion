package com.example.classicfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.classicfashion.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
