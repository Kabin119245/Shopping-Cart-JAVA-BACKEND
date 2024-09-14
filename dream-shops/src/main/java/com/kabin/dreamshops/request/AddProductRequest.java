package com.kabin.dreamshops.request;

import com.kabin.dreamshops.model.Category;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {

    private Long id;
    private String name;
    private String brand;

    private BigDecimal price;

    //quantity in stock not in cart
    private  int inventory;

    private String description;

    private Category category;


}
