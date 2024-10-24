package com.example.classicfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.classicfashion.model.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
	 Size findBySizeName(String sizeName);

}
	