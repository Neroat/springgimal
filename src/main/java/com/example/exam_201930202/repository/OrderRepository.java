package com.example.exam_201930202.repository;

import com.example.exam_201930202.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findAllByUserId(String userId);

    List<Order> findAllByProductId(Long productId);

    Order findOrderById(Long id);
}
