package com.kabin.dreamshops.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;

    private BigDecimal price;

    //quantity in stock not in cart
    private  int inventory;

    private String description;


    //relationships
    @ManyToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Image> images;


    public Product(String name, String brand, String description, int inventory, BigDecimal price, Category category) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.inventory = inventory;
        this.price = price;
        this.category = category;
    }
}
