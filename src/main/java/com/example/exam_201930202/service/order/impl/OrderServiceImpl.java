package com.example.exam_201930202.service.order.impl;

import com.example.exam_201930202.dao.order.OrderDAO;
import com.example.exam_201930202.dao.product.ProductDAO;
import com.example.exam_201930202.dto.order.OrderDto;
import com.example.exam_201930202.dto.order.OrderResponseDto;
import com.example.exam_201930202.entity.Order;
import com.example.exam_201930202.entity.Product;
import com.example.exam_201930202.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, ProductDAO productDAO) {
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
    }

    @Override
    public OrderResponseDto insertOrder(OrderDto orderDto, String productName, int price, String userId, String username) throws Exception{

        if(productDAO.selectProduct(orderDto.getProductId()).getStock() > 0) {
            Order order = new Order();
            order.setProductId(orderDto.getProductId());
            order.setProductName(productName);
            order.setUserId(userId);
            order.setUserName(username);
            order.setPrice(price);
            order.setCreatedAt(LocalDateTime.now());

            Order insertOrder = orderDAO.insertOrder(order);
            OrderResponseDto orderResponseDto = new OrderResponseDto(insertOrder);

            Product orderProduct = productDAO.selectProduct(orderDto.getProductId());
            productDAO.minusStock(orderProduct);

            return orderResponseDto;

        } else throw new Exception("재고가 없습니다.");
    }

    @Override
    public List<OrderResponseDto> getListOrder() {
        List<Order> orderList = orderDAO.allOrder();
        List<OrderResponseDto> orderResponseDtoList = orderList.stream().map(order ->
                new OrderResponseDto(order)).collect(Collectors.toList());
        return orderResponseDtoList;
    }

    @Override
    public List<OrderResponseDto> getListOrderByUserId(String userId) {
        List<Order> orderList = orderDAO.listOrderByUserId(userId);
        List<OrderResponseDto> orderResponseDtoList = orderList.stream().map(order ->
                new OrderResponseDto(order)).collect(Collectors.toList());
        return orderResponseDtoList;
    }

    @Override
    public List<OrderResponseDto> getListOrderByProductId(Long productId) {
        List<Order> orderList = orderDAO.listOrderByProductId(productId);
        List<OrderResponseDto> orderResponseDtoList = orderList.stream().map(order ->
                new OrderResponseDto(order)).collect(Collectors.toList());
        return orderResponseDtoList;
    }

    @Override
    public OrderResponseDto selectOrder(Long id) {
        Order selectOrder = orderDAO.selectOrder(id);
        return new OrderResponseDto(selectOrder);
    }

}
