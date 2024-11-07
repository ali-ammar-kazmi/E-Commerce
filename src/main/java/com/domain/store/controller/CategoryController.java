package com.domain.store.controller;

import com.domain.store.dto.CategoryDto;
import com.domain.store.model.Category;
import com.domain.store.response.ApiResponse;
import com.domain.store.services.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/category")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/retrieve/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try{
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found All!", categories));
        } catch ( Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/save/{categoryName}")
    public ResponseEntity<ApiResponse> addCategory(@PathVariable String categoryName){
        try{
            CategoryDto category = categoryService.addCategory(categoryName);
            return ResponseEntity.ok(new ApiResponse("Category Added!", category));
        } catch ( Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/retrieveById/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
        try{
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Category Found!", category));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/retrieveByName/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName){
        try{
            Category category = categoryService.getCategoryByName(categoryName);
            return ResponseEntity.ok(new ApiResponse("Category Found!", category));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/update/{categoryId}/{categoryName}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable String categoryName, @PathVariable Long categoryId){
        try{
            Category category = categoryService.updateCategory(categoryName, categoryId);
            return ResponseEntity.ok(new ApiResponse("Category Updated!", category));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId){
        try{
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(new ApiResponse("Category Deleted!", null));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }


}
