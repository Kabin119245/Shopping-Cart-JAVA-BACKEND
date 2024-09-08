package com.kabin.dreamshops.model;

import java.math.BigDecimal;
import java.util.List;

public class Product {

    private Long id;
    private String name;
    private String brand;

    private BigDecimal price;

    //quantity in stock not in cart
    private  int inventory;

    private String description;


    //relationships
    private Category category;

    private List<Image> images;




}
