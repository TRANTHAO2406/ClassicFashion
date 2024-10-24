package com.example.classicfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.classicfashion.model.Order;
import java.util.List;
import com.example.classicfashion.model.Users;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUser(Users users);

}
