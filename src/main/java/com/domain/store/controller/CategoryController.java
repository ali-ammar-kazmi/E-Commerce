package com.domain.store.controller;

import com.domain.store.dto.CategoryDto;
import com.domain.store.exception.FoundException;
import com.domain.store.helper.ConvertToDto;
import com.domain.store.model.Category;
import com.domain.store.response.ApiResponse;
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
    private final ConvertToDto convert;

    @GetMapping("/retrieve/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try{
            List<Category> categories = categoryService.getAllCategories();
            List<CategoryDto> categoryDto = convert.convertCategoryToDtos(categories);
            return ResponseEntity.ok(new ApiResponse("Found All!", categoryDto));
        } catch ( Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
        }
    }

    @PostMapping("/save/{categoryName}")
    public ResponseEntity<ApiResponse> addCategory(@PathVariable String categoryName){
        try{
            Category category = categoryService.addCategory(categoryName);
            CategoryDto categoryDto = convert.convertCategoryToDto(category);
            return ResponseEntity.ok(new ApiResponse("Category Added!", categoryDto));
        } catch ( FoundException e){
            return ResponseEntity.status(FOUND).body(new ApiResponse(e.getMessage(), FOUND));
        } catch ( Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/retrieveById/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
        try{
            Category category = categoryService.getCategoryById(categoryId);
            CategoryDto categoryDto = convert.convertCategoryToDto(category);
            return ResponseEntity.ok(new ApiResponse("Category Found!", categoryDto));
        } catch ( FoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/retrieveByName/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName){
        try{
            Category category = categoryService.getCategoryByName(categoryName);
            CategoryDto categoryDto = convert.convertCategoryToDto(category);
            return ResponseEntity.ok(new ApiResponse("Category Found!", categoryDto));
        } catch ( FoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/update/{categoryId}/{categoryName}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable String categoryName, @PathVariable Long categoryId){
        try{
            Category category = categoryService.updateCategory(categoryName, categoryId);
            CategoryDto categoryDto = convert.convertCategoryToDto(category);
            return ResponseEntity.ok(new ApiResponse("Category Updated!", categoryDto));
        } catch ( FoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId){
        try{
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(new ApiResponse("Category Deleted!", null));
        } catch ( FoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }
}
