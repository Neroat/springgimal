package com.example.exam_201930202.service.order;

import com.example.exam_201930202.dto.order.OrderDto;
import com.example.exam_201930202.dto.order.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto insertOrder(OrderDto orderDto, String productName, int price, String userId, String username) throws Exception;

    List<OrderResponseDto> getListOrder();

    List<OrderResponseDto> getListOrderByUserId(String userId);

    List<OrderResponseDto> getListOrderByProductId(Long productId);

    OrderResponseDto selectOrder(Long id);
}
