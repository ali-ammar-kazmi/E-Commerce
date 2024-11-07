package com.domain.store.services.product;

import java.util.List;
import com.domain.store.model.Product;
import com.domain.store.request.ProductRequest;

public interface IProductService {

    Product getProductById(Long id);
    Product addProduct(ProductRequest request);
    Product updateProduct(ProductRequest request, long id);
    void deleteProduct(long id);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);

    int countProductsByBrandAndName(String brand, String Name);
}
