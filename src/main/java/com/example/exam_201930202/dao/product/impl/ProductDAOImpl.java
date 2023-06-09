package com.example.exam_201930202.dao.product.impl;

import com.example.exam_201930202.dao.product.ProductDAO;
import com.example.exam_201930202.entity.Product;
import com.example.exam_201930202.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ProductDAOImpl implements ProductDAO {

    private final ProductRepository productRepository;

    @Autowired
    public ProductDAOImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product updateProductInfo(Long number, String name, int price, int stock) throws Exception {
        Optional<Product> selectedProduct = productRepository.findById(number);

        Product updateProduct;
        if(selectedProduct.isPresent()) {
            Product product = selectedProduct.get();
            product.setName(name);
            product.setPrice(price);
            product.setStock(stock);
            product.setUpdatedAt(LocalDateTime.now());

            updateProduct = productRepository.save(product);
        } else throw new Exception();

        return updateProduct;
    }

    @Override
    public Product insertProduct(Product product) {
        Product saveProduct = productRepository.save(product);
        return saveProduct;
    }

    @Override
    public void deleteProduct(Long number) throws Exception {
        Optional<Product> selectedProduct = productRepository.findById(number);

        if(selectedProduct.isPresent()) {
            Product product = selectedProduct.get();
            productRepository.delete(product);
        } else throw new Exception();
    }

    @Override
    public List<Product> allProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> listProductByPriceOrderByDesc() {
        return productRepository.findAllProductByOrderByPriceDesc();
    }

    @Override
    public List<Product> listProductByName(String name) {
        return productRepository.findProductsByName(name);
    }

    @Override
    public Product selectProduct(Long number) {
        return productRepository.findProductByNumber(number);
    }

    @Override
    public Product minusStock(Product product) throws Exception{
        Optional<Product> selectedProduct = productRepository.findById(product.getNumber());

        Product updateProduct;
        if(selectedProduct.isPresent()) {
            Product newProduct = selectedProduct.get();
            newProduct.setStock(newProduct.getStock()-1);
            newProduct.setUpdatedAt(LocalDateTime.now());

            updateProduct = productRepository.save(newProduct);
        } else throw new Exception();

        return updateProduct;
    }
}
