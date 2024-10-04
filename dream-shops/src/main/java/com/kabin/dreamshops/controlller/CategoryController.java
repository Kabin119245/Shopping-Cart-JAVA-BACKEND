package com.kabin.dreamshops.controlller;


import com.kabin.dreamshops.exception.ALreadyExisitsException;
import com.kabin.dreamshops.model.Category;
import com.kabin.dreamshops.response.ApiResponse;
import com.kabin.dreamshops.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Categories fetched successfully!", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error! Categories not found!",INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name ) {

        try {
            Category newCategory = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Category added successfully!", newCategory));
        } catch (ALreadyExisitsException e) {
          return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            Category theCategory = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Category Found!", theCategory));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error! Categories not found!",NOT_FOUND));
        }
    }

    //find category by name
    @GetMapping("category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try {
            Category theCategory = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Category Found!", theCategory));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error! Categories not found!", NOT_FOUND));
        }
    }


        //delete category , we need ID

        @DeleteMapping("/category/{id}/delete")
        public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
            try {
                categoryService.deleteCategoryById(id);
                return ResponseEntity.ok(new ApiResponse("Category Found!", null));
            } catch (Exception e) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error! Categories not found!",NOT_FOUND));
            }
        }

        //update the category

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {

        try {
            Category updatedCategory = categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("Category updated successfully!", updatedCategory));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null
            ));
        }

    }

}
