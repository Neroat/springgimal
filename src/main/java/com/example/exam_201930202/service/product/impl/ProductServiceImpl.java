package com.example.exam_201930202.service.product.impl;

import com.example.exam_201930202.dao.product.ProductDAO;
import com.example.exam_201930202.dto.product.ProductDto;
import com.example.exam_201930202.dto.product.ProductResponseDto;
import com.example.exam_201930202.entity.Product;
import com.example.exam_201930202.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public ProductResponseDto changeProductInfo(Long number, String name, int price, int stock) throws Exception {
        Product changeProduct = productDAO.updateProductInfo(number, name, price, stock);
        return new ProductResponseDto(changeProduct);
    }

    @Override
    public ProductResponseDto saveProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setCreatedAt(LocalDateTime.now());

        Product saveProduct = productDAO.insertProduct(product);

        ProductResponseDto productResponseDto = new ProductResponseDto(saveProduct);
        return productResponseDto;
    }

    @Override
    public void deleteProduct(Long number) throws Exception {
        productDAO.deleteProduct(number);
    }

    @Override
    public List<ProductResponseDto> getListProducts() {
        List<Product> productList = productDAO.allProduct();
        List<ProductResponseDto> productResponseDtoList = productList.stream().map(product ->
                new ProductResponseDto(product)).collect(Collectors.toList());
        return productResponseDtoList;
    }

    @Override
    public List<ProductResponseDto> getListProductsOrderByPriceDesc() {
        List<Product> productList = productDAO.listProductByPriceOrderByDesc();
        List<ProductResponseDto> productResponseDtoList = productList.stream().map(product ->
                new ProductResponseDto(product)).collect(Collectors.toList());
        return productResponseDtoList;
    }

    @Override
    public List<ProductResponseDto> getListProductsByName(String name) {
        List<Product> productList = productDAO.listProductByName(name);
        List<ProductResponseDto> productResponseDtoList = productList.stream().map(product ->
                new ProductResponseDto(product)).collect(Collectors.toList());
        return productResponseDtoList;
    }

    @Override
    public ProductResponseDto getProductByNumber(Long number) {
        Product selectProduct = productDAO.selectProduct(number);
        return new ProductResponseDto(selectProduct);
    }
}
