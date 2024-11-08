package com.domain.store.services.category;

import com.domain.store.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    Category addCategory(String name);
    Category updateCategory(String name, Long id);
    void deleteCategory(Long id);
    List<Category> getAllCategories();
}
