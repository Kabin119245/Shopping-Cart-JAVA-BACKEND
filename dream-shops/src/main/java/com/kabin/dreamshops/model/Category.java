package com.kabin.dreamshops.model;

import java.util.List;

public class Category {

    private Long id;

    private String name;

    //relationships
    private List<Product> products;
}
