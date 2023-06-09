package com.example.exam_201930202.dao.product;

import com.example.exam_201930202.entity.Product;

import java.util.List;

public interface ProductDAO {
    Product updateProductInfo(Long number, String name, int price, int stock) throws Exception;

    Product insertProduct(Product product);

    void deleteProduct(Long number) throws Exception;

    List<Product> allProduct();

    List<Product> listProductByPriceOrderByDesc();

    List<Product> listProductByName(String name);

    Product selectProduct(Long number);

    Product minusStock(Product product) throws Exception;

}
