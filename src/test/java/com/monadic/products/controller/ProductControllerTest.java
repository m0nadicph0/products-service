package com.monadic.products.controller;

import com.monadic.products.model.Product;
import com.monadic.products.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProduct() {
        Product product = createProduct("Fusion Blender", true, true);
        when(productService.saveProduct(product)).thenReturn(product);
        ResponseEntity<Product> responseEntity = productController.addProduct(product);
        assertEquals(product.getId(), Objects.requireNonNull(responseEntity.getBody()).getId());
        assertEquals(true, Objects.requireNonNull(responseEntity.getBody()).getActive());
        assertEquals(true, Objects.requireNonNull(responseEntity.getBody()).getShippable());
    }

    private static Product createProduct(String name, boolean active, boolean shippable) {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(name);
        product.setActive(active);
        product.setShippable(shippable);
        return product;
    }


    @Test
    void getAllProducts() {
        List<Product> productList = List.of(
                createProduct("Fusion Blender", true, true),
                createProduct("Quantum Headphones", true, true),
                createProduct("Solar Charger", true, true),
                createProduct("Stellar Telescope", true, true),
                createProduct("Midnight Espresso Machine", true, true),
                createProduct("Zenith Smartwatch", true, true)
        );
        when(productService.getAllProducts()).thenReturn(productList);
        ResponseEntity<List<Product>> responseEntity = productController.getAllProducts();
        assertEquals(6, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    void getProductById() {
        Product product = createProduct("Fusion Blender", true, true);
        when(productService.getProductById(product.getId())).thenReturn(product);
        ResponseEntity<Product> responseEntity = productController.getProductById(product.getId());
        assertEquals(product.getId(), Objects.requireNonNull(responseEntity.getBody()).getId());
        assertEquals("Fusion Blender", Objects.requireNonNull(responseEntity.getBody()).getName());
        assertEquals(true, Objects.requireNonNull(responseEntity.getBody()).getActive());
        assertEquals(true, Objects.requireNonNull(responseEntity.getBody()).getShippable());
    }

    @Test
    void deleteProduct() {
        String id = UUID.randomUUID().toString();
        productController.deleteProduct(id);
        verify(productService, times(1)).deleteProduct(id);
    }
}