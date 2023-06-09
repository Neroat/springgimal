package com.example.exam_201930202.service.product;

import com.example.exam_201930202.dto.product.ProductDto;
import com.example.exam_201930202.dto.product.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto changeProductInfo(Long number, String name, int price, int stock) throws Exception;

    ProductResponseDto saveProduct(ProductDto productDto);

    void deleteProduct(Long number) throws Exception;

    List<ProductResponseDto> getListProducts();

    List<ProductResponseDto> getListProductsOrderByPriceDesc();

    List<ProductResponseDto> getListProductsByName(String name);

    ProductResponseDto getProductByNumber(Long number);
}
