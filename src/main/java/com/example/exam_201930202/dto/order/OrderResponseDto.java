package com.example.exam_201930202.dto.order;

import com.example.exam_201930202.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long productId;
    private String productName;
    private String userId;
    private String username;
    private int price;
    private LocalDateTime orderAt;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.productId = order.getProductId();
        this.productName = order.getProductName();
        this.userId = order.getUserId();
        this.username = order.getUserName();
        this.orderAt = order.getCreatedAt();
        this.price = order.getPrice();
    }
}
