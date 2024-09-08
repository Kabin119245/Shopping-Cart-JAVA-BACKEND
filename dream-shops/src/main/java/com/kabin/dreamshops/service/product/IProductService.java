package com.kabin.dreamshops.service.product;

import com.kabin.dreamshops.model.Product;

import java.util.List;

public interface IProductService {

    Product addProduct(Product product);

    Product getProductById(Long id);

    void updateProduct(Product product, Long productId);

    void deleteProductById(Long id);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String category, String name);

    Long countProductsByBrandAndName(String brand, String name);

}
