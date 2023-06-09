package com.example.exam_201930202.dao.order;

import com.example.exam_201930202.entity.Order;

import java.util.List;

public interface OrderDAO {

    Order insertOrder(Order order);

    List<Order> allOrder();

    List<Order> listOrderByUserId(String userId);

    List<Order> listOrderByProductId(Long productId);

    Order selectOrder(Long id);
}
