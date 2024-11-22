package com.domain.store.services.product;

import com.domain.store.exception.StoreException;
import com.domain.store.model.Category;
import com.domain.store.model.Product;
import com.domain.store.repository.ProductRepository;
import com.domain.store.request.ProductRequest;
import com.domain.store.services.category.CategoryService;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new StoreException("Product Not Found with id: "+ id));
    }

    @Override
    public Product addProduct(ProductRequest request) {
        try {
            String name = request.getCategoryName();
            Category category = categoryService.addCategory(name);
            Product product = new Product();
            product.setName(request.getName());
            product.setBrand(request.getBrand());
            product.setPrice(request.getPrice());
            product.setInventory(request.getInventory());
            product.setDescription(request.getDescription());
            product.setCategory(category);
            return productRepository.save(product);
        } catch (Exception e) {
            throw new StoreException("Product Not Added!");
        }
    }

    @Override
    public Product updateProduct(long id, ProductRequest request) {
        return productRepository.findById(id)
                .map((existingProduct) -> {
                    existingProduct.setName(request.getName());
                    existingProduct.setBrand(request.getBrand());
                    existingProduct.setPrice(request.getPrice());
                    existingProduct.setInventory(request.getInventory());
                    existingProduct.setDescription(request.getDescription());
                    Category category = categoryService.getCategoryByName(request.getCategoryName());
                    existingProduct.setCategory(category);
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(()-> new StoreException("Product Not Found with id: "+ id));
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, ()-> {throw new StoreException("Product Not Found with id: "+ id);});
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {

        return productRepository.findByCategoryName(categoryName);
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
    public List<Product> getProductsByCategoryAndName(String category, String name) {
        return productRepository.findByCategoryNameAndName(category, name);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public int countProductsByCategory(String categoryName) {
        return productRepository.findByCategoryName(categoryName).size();
    }
}
