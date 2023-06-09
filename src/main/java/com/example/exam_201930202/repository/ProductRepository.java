package com.example.exam_201930202.repository;

import com.example.exam_201930202.entity.Product;
import com.example.exam_201930202.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllProductByOrderByPriceDesc();

    List<Product> findProductsByName(String name);

    Product findProductByNumber(Long number);
}
