package com.monadic.products.service;

import com.monadic.products.model.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(String id);

    void deleteProduct(String id);
}