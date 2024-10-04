package com.kabin.dreamshops.service.product;

import com.kabin.dreamshops.dto.ImageDto;
import com.kabin.dreamshops.dto.ProductDto;
import com.kabin.dreamshops.exception.ProductNotFoundException;
import com.kabin.dreamshops.model.Category;
import com.kabin.dreamshops.model.Image;
import com.kabin.dreamshops.model.Product;
import com.kabin.dreamshops.repository.CategoryRepository;
import com.kabin.dreamshops.repository.ImageRepository;
import com.kabin.dreamshops.repository.ProductRepository;
import com.kabin.dreamshops.request.AddProductRequest;
import com.kabin.dreamshops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService implements  IProductService{


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;


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

      return  productRepository.save(createProduct(request, category));
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
    public Product updateProduct(ProductUpdateRequest request, Long productId) {

      return  productRepository.findById(productId)
                .map(existingProduct -> updatExistingProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }


    private Product updatExistingProduct(Product existingProduct, ProductUpdateRequest updateRequest) {
        existingProduct.setName(updateRequest.getName());
        existingProduct.setBrand(updateRequest.getBrand());
        existingProduct.setInventory(updateRequest.getInventory());
        existingProduct.setDescription(updateRequest.getDescription());
        existingProduct.setPrice(updateRequest.getPrice());

        Category category = categoryRepository.findByName(updateRequest.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;

    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }


    @Override
    public void deleteProductById(Long id) {

        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        ()-> {throw new ProductNotFoundException("Product not found");});


    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countProductsByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imagesDto = images.stream().map(image -> modelMapper.map(image, ImageDto.class))
                .toList();

        productDto.setImages(imagesDto);
        return productDto;
    }
}
