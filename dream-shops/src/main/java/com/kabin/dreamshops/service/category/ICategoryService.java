package com.kabin.dreamshops.service.category;

import com.kabin.dreamshops.model.Category;

import java.util.List;

public interface ICategoryService {

    Category getCategoryById(Long id);
    Category addCategory(Category category);
    Category getCategoryByName(String name);

    List<Category> getAllCategories();

    Category updateCategory(Category category , Long id);

    void deleteCategoryById(Long id);




}
