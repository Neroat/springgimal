package com.example.exam_201930202.controller;

import com.example.exam_201930202.config.security.JwtTokenProvider;
import com.example.exam_201930202.dto.order.OrderDto;
import com.example.exam_201930202.dto.order.OrderResponseDto;
import com.example.exam_201930202.service.order.OrderService;
import com.example.exam_201930202.service.product.ProductService;
import com.example.exam_201930202.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, ProductService productService, JwtTokenProvider jwtTokenProvider) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Operation(summary = "주문등록 - ProductId 필요 - USER만 가능 -", description = "토큰값을 활용하여 Username, UserID 및 DAO를 추가 확장하여 Product 이름 자동 기입 ")
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<OrderResponseDto> insertOrder(HttpServletRequest request, @RequestBody OrderDto orderDto) throws Exception {
        String loginUserId = jwtTokenProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));
        String loginUsername = userService.getUsernameByuId(loginUserId);
        String productName = productService.getProductByNumber(orderDto.getProductId()).getName();
        int productPrice = productService.getProductByNumber(orderDto.getProductId()).getPrice();
        OrderResponseDto orderResponseDto = orderService.insertOrder(orderDto, productName, productPrice, loginUserId, loginUsername);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }

    @Operation(summary = "주문 목록 -ADMIN만 가능- ")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> listOrder() {
        List<OrderResponseDto> orderResponseDtoList = orderService.getListOrder();
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDtoList);
    }

    @Operation(summary = "상품별 주문 목록 (userID 필요) -ADMIN만 가능- ")
    @GetMapping("/listByUserId")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> listOrderByUserId(@RequestParam String userId) {
        List<OrderResponseDto> orderResponseDtoList = orderService.getListOrderByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDtoList);
    }

    @Operation(summary = "상품별 주문 목록 (productID 필요) -ADMIN만 가능- ")
    @GetMapping("/listByProductId")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> listOrderByProductId(@RequestParam Long productId) {
        List<OrderResponseDto> orderResponseDtoList = orderService.getListOrderByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDtoList);
    }

    @Operation(summary = "주문 정보 (OrderID 필요) -ADMIN만 가능- ")
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderResponseDto> selectOrder(@RequestParam Long id) {
        OrderResponseDto orderResponseDto = orderService.selectOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }
}
