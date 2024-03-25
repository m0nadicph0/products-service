package com.monadic.products.util;

import com.monadic.products.model.Product;

public class ProductUtil {
    public static Product createProduct(String name, boolean active, boolean shippable) {
        return Product.builder()
                .name(name)
                .active(active)
                .shippable(shippable)
                .build();
    }
}
