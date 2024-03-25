package com.monadic.products;

import com.monadic.products.model.Product;
import com.monadic.products.repository.ProductRepository;
import com.monadic.products.util.ProductUtil;
import com.monadic.products.util.UrlUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Configuration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ProductIntegrationTest {

    public static final String PRODUCTS = "products";
    @Autowired
    private  ProductRepository productRepository;

    private final RestTemplate restTemplate = new RestTemplate();



    @LocalServerPort
    private Integer port;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:latest"
    );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }


    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }



    @Test
    void testGetAllProducts() {
        List<Product> products = List.of(
                Product.builder().name("Fusion Blender").shippable(true).active(true).build(),
                Product.builder().name("Quantum Headphones").shippable(true).active(true).build(),
                Product.builder().name("Solar Charger").shippable(true).active(true).build()
        );
        productRepository.saveAll(products);

        ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity(UrlUtil.urlFor(PRODUCTS, port), Product[].class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(3, Objects.requireNonNull(responseEntity.getBody()).length);

    }

    @Test
    void testGetProductById() {
        Product product = ProductUtil.createProduct("Quantum Headphones", true, true);
        productRepository.save(product);


        ResponseEntity<Product> responseProduct = restTemplate
                .getForEntity(UrlUtil.singularUrlFor(PRODUCTS, product.getId(), port), Product.class);

        assertEquals(HttpStatus.OK, responseProduct.getStatusCode());
        assertEquals("Quantum Headphones", Objects.requireNonNull(responseProduct.getBody()).getName());
        assertEquals(true, responseProduct.getBody().getActive());
        assertEquals(true, responseProduct.getBody().getShippable());
    }

    @Test
    void testCreateProduct() {
        Product product = ProductUtil.createProduct("Stellar Telescope", true, true);
        ResponseEntity<Product> responseProduct = restTemplate.postForEntity(UrlUtil.urlFor(PRODUCTS, port), product, Product.class);
        assertEquals(HttpStatus.CREATED, responseProduct.getStatusCode());
        assertEquals("Stellar Telescope", Objects.requireNonNull(responseProduct.getBody()).getName());
        assertEquals(true, responseProduct.getBody().getActive());
        assertEquals(true, responseProduct.getBody().getShippable());
    }

    @Test
    void testDeleteProduct() {
        Product product = ProductUtil.createProduct("Stellar Telescope", true, true);
        productRepository.save(product);
        restTemplate.delete(UrlUtil.singularUrlFor(PRODUCTS, product.getId(), port));
        List<Product> products = productRepository.findAll();
        assertEquals(0, products.size());
    }


}
