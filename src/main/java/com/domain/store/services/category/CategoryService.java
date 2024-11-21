package com.domain.store.services.category;

import com.domain.store.exception.FoundException;
import com.domain.store.model.Category;
import com.domain.store.repository.CategoryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new FoundException("Category Not Found with id: " + id));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(()-> new FoundException("Category Not Found with name: " + name));
    }

    @Override
    public Category addCategory(String name) {
        if (categoryRepository.findByName(name).isEmpty()){
            Category category = new Category();
            category.setName(name);
            categoryRepository.save(category);
            return category;
        }else{
            return getCategoryByName(name);
        }
    }

    @Override
    public Category updateCategory(String categoryName, Long id) {
        return categoryRepository.findById(id).map(oldCategory -> {
            oldCategory.setName(categoryName);
            return categoryRepository.save(oldCategory);
        }).orElseThrow(()->
            new FoundException("Category Not Found with id: " + id));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, ()-> {
                    throw new FoundException("Category Not Found with id: " + id);});
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}
