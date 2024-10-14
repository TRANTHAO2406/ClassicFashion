package com.example.classicfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.classicfashion.model.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long>  {
	Color findByColorName(String colorName);
}
