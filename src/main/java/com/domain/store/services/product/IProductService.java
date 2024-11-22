package com.domain.store.services.product;

import java.util.List;

import com.domain.store.model.Product;
import com.domain.store.request.ProductRequest;

public interface IProductService {

    Product getProductById(Long id);
    Product addProduct(ProductRequest request);
    Product updateProduct(long id, ProductRequest request);
    void deleteProduct(long id);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByCategoryAndName(String category, String name);

    int countProductsByCategory(String category);
}
