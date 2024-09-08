package com.kabin.dreamshops.request;

import com.kabin.dreamshops.model.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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


    //relationships
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;


}
