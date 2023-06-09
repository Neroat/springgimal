package com.example.exam_201930202.dao.order.impl;

import com.example.exam_201930202.dao.order.OrderDAO;
import com.example.exam_201930202.entity.Order;
import com.example.exam_201930202.repository.OrderRepository;
import com.example.exam_201930202.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDAOImpl implements OrderDAO {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderDAOImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order insertOrder(Order order) {
        Order insertOrder = orderRepository.save(order);
        return insertOrder;
    }

    @Override
    public List<Order> allOrder() {
        List<Order> orderList = orderRepository.findAll();
        return orderList;
    }

    @Override
    public List<Order> listOrderByUserId(String userId) {
        List<Order> orderList = orderRepository.findAllByUserId(userId);
        return orderList;
    }

    @Override
    public List<Order> listOrderByProductId(Long productId) {
        List<Order> orderList = orderRepository.findAllByProductId(productId);
        return orderList;
    }

    @Override
    public Order selectOrder(Long id) {
        Order selectOrder = orderRepository.findOrderById(id);
        return selectOrder;
    }
}
