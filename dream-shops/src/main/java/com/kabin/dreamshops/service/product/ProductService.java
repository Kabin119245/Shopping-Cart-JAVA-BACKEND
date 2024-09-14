package com.kabin.dreamshops.service.product;

import com.kabin.dreamshops.exception.ProductNotFoundException;
import com.kabin.dreamshops.model.Category;
import com.kabin.dreamshops.model.Product;
import com.kabin.dreamshops.repository.CategoryRepository;
import com.kabin.dreamshops.repository.ProductRepository;
import com.kabin.dreamshops.request.AddProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements  IProductService{


    private final ProductRepository prepo;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request ) {
        //check if category is found in DB
        // if yes -> set it as a new product category
        //  if np-> save it as a new category
        //then set as a new product category

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName())).orElseGet(() -> {
            Category newCategory = new Category(request.getCategory().getName());
            return categoryRepository.save(newCategory);
        });
        request.setCategory(category);

      return  prepo.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {

        return new Product(
           request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getInventory(),
                request.getPrice(),
                category
        );

    }


    @Override
    public void updateProduct(Product product, Long productId) {
    }

    @Override
    public Product getProductById(Long id) {
        return prepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }


    @Override
    public void deleteProductById(Long id) {

        prepo.findById(id)
                .ifPresentOrElse(prepo::delete,
                        ()-> {throw new ProductNotFoundException("Product not found");});


    }

    @Override
    public List<Product> getAllProducts() {
        return prepo.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return prepo.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return prepo.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return prepo.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return prepo.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return prepo.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return prepo.countProductsByBrandAndName(brand,name);
    }
}
