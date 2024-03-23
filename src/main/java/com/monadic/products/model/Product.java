package com.monadic.products.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "created")
    private Long created;

    @Column(name = "default_price")
    private BigDecimal defaultPrice;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "shippable")
    private Boolean shippable;

    @Column(name = "statement_descriptor", length = 200)
    private String statementDescriptor;

    @Column(name = "tax_code", length = 50)
    private String taxCode;

    @Column(name = "unit_label", length = 50)
    private String unitLabel;

    @Column(name = "updated")
    private Long updated;

    @Column(name = "url", length = 500)
    private String url;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
}