package com.domain.store.repository;

import com.domain.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryName(String categoryName);

    List<Product> findByCategoryNameAndBrand(String categoryName, String brand);

    List<Product> findByCategoryNameAndName(String categoryName, String name);
}
