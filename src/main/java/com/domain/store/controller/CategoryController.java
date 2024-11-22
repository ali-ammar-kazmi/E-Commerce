package com.domain.store.controller;

import com.domain.store.exception.StoreException;
import com.domain.store.model.Category;
import com.domain.store.response.StoreResponse;
import com.domain.store.services.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/category")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/retrieve/all")
    public ResponseEntity<StoreResponse> getAllCategories(){
        try{
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new StoreResponse("Found All!", categories));
        } catch ( Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Found Failed!", NOT_FOUND));
        }
    }

    @PostMapping("/save/{categoryName}")
    public ResponseEntity<StoreResponse> addCategory(@PathVariable String categoryName){
        try{
            Category category = categoryService.addCategory(categoryName);
            return ResponseEntity.ok(new StoreResponse("Category Added!", category));
        } catch ( Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new StoreResponse("Save Failed!", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/retrieveById/{categoryId}")
    public ResponseEntity<StoreResponse> getCategoryById(@PathVariable Long categoryId){
        try{
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new StoreResponse("Category Found!", category));
        } catch ( StoreException e){
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Category Not Found!", NOT_FOUND));
        }
    }

    @GetMapping("/retrieveByName/{categoryName}")
    public ResponseEntity<StoreResponse> getCategoryByName(@PathVariable String categoryName){
        try{
            Category category = categoryService.getCategoryByName(categoryName);
            return ResponseEntity.ok(new StoreResponse("Category Found!", category));
        } catch ( StoreException e){
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Category Not Found!", NOT_FOUND));
        }
    }

    @PutMapping("/update/{categoryId}/{categoryName}")
    public ResponseEntity<StoreResponse> updateCategory(@PathVariable Long categoryId, @PathVariable String categoryName){
        try{
            Category category = categoryService.updateCategory(categoryId, categoryName);
            return ResponseEntity.ok(new StoreResponse("Category Updated!", category));
        } catch ( StoreException e){
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Update Failed!", NOT_FOUND));
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<StoreResponse> deleteCategory(@PathVariable Long categoryId){
        try{
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(new StoreResponse("Category Deleted!", null));
        } catch ( StoreException e){
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Delete Failed!", NOT_FOUND));
        }
    }
}
