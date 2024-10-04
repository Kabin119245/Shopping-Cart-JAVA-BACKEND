package com.kabin.dreamshops.dto;

import com.kabin.dreamshops.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    //quantity in stock not in cart
    private  int inventory;
    private String description;
    private Category category;
    private List<ImageDto> images;

}
