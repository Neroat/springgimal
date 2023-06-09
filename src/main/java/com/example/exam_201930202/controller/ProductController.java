package com.example.exam_201930202.controller;

import com.example.exam_201930202.dto.product.ChangeProductDto;
import com.example.exam_201930202.dto.product.ProductDto;
import com.example.exam_201930202.dto.product.ProductResponseDto;
import com.example.exam_201930202.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "상품 수정 (ProductId 필요) -ADMIN만 가능- ")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<ProductResponseDto> changeProductInfo(@RequestBody ChangeProductDto changeProductDto) throws Exception {
        ProductResponseDto productResponseDto = productService.changeProductInfo(changeProductDto.getNumber(), changeProductDto.getName(), changeProductDto.getPrice(), changeProductDto.getStock());
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }

    @Operation(summary = "상품 등록 -ADMIN만 가능- ")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductDto productDto) {
        ProductResponseDto productResponseDto = productService.saveProduct(productDto);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }

    @Operation(summary = "상품 제거 -ADMIN만 가능- ")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<String> deleteProduct(Long number) throws Exception {
        productService.deleteProduct(number);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었음.");
    }

    @Operation(summary = "상품 리스트 -누구나-")
    @GetMapping("/list")
    public ResponseEntity<List<ProductResponseDto>> listProducts() {
        List<ProductResponseDto> productResponseDtoList = productService.getListProducts();
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDtoList);
    }

    @Operation(summary = "상품 리스트 - 가격순(내림차순) - 누구나")
    @GetMapping("/listOrderByPrice")
    public ResponseEntity<List<ProductResponseDto>> listProductsOrderByPrice() {
        List<ProductResponseDto> productResponseDtoList = productService.getListProductsOrderByPriceDesc();
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDtoList);
    }

    @Operation(summary = "상품 리스트 - 이름(중복가능) - 누구나")
    @GetMapping("/byName")
    public ResponseEntity<List<ProductResponseDto>> listProductsByName(@RequestParam String name) {
        List<ProductResponseDto> productResponseDtoList = productService.getListProductsByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDtoList);
    }

    @Operation(summary = "ProductId로 정보 가져오기 - 누구나 -")
    @GetMapping
    public ResponseEntity<ProductResponseDto> productByNumber(@RequestParam Long number) {
        ProductResponseDto productResponseDto = productService.getProductByNumber(number);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }
}
