package com.domain.store.controller;

import com.domain.store.model.Product;
import com.domain.store.request.ProductRequest;
import com.domain.store.response.StoreResponse;
import com.domain.store.services.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/product")
public class ProductController {
    private final IProductService productService;

    @PostMapping("/save")
    public ResponseEntity<StoreResponse> addProduct(@RequestBody ProductRequest productRequest){
        try {
            Product product = productService.addProduct(productRequest);
            return ResponseEntity.ok(new StoreResponse("Save Success!", product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new StoreResponse("Save Failed!", INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<StoreResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductRequest productRequest){
        try {
            Product product = productService.updateProduct(productId, productRequest);
            return ResponseEntity.ok(new StoreResponse("Update Success!", product));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Update Failed!", NOT_FOUND));
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<StoreResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new StoreResponse("Delete Success!", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Delete Failed!", NOT_FOUND));
        }
    }

    @GetMapping("/retrieve/{productId}")
    public ResponseEntity<StoreResponse> getProductById(@PathVariable Long productId){
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(new StoreResponse("Found Success!", product));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Found Failed!", NOT_FOUND));
        }
    }

    @GetMapping("/retrieve/all")
    public ResponseEntity<StoreResponse> getAllProducts(){
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(new StoreResponse("Found Success!", products));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Found Failed!", NOT_FOUND));
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<StoreResponse> getProductsByCategory(@PathVariable String categoryName){
        try {
            List<Product> products = productService.getProductsByCategory(categoryName);
            return ResponseEntity.ok(new StoreResponse("Found Success!", products));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Found Failed!", NOT_FOUND));
        }
    }

    @GetMapping("/brand/{brandName}")
    public ResponseEntity<StoreResponse> getProductsByBrand(@PathVariable String brandName){
        System.out.println(brandName);
        try {
            List<Product> products = productService.getProductsByBrand(brandName);
            return ResponseEntity.ok(new StoreResponse("Found Success!", products));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Found Failed!", NOT_FOUND));
        }
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<StoreResponse> getProductsByName(@PathVariable String productName){
        try {
            List<Product> products = productService.getProductsByName(productName);
            return ResponseEntity.ok(new StoreResponse("Found Success!", products));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Found Failed!", NOT_FOUND));
        }
    }

    @GetMapping("/category/{categoryName}/brand/{brandName}")
    public ResponseEntity<StoreResponse> getProductsByCategoryAndBrand(@PathVariable String categoryName, @PathVariable String brandName){
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brandName);
            return ResponseEntity.ok(new StoreResponse("Found Success!", products));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Found Failed!", NOT_FOUND));
        }
    }

    @GetMapping("/categoryName/{categoryName}")
    public ResponseEntity<StoreResponse> countProductsByCategory(@PathVariable String categoryName){
        try {
            int count = productService.countProductsByCategory(categoryName);
            return ResponseEntity.ok(new StoreResponse("Found Success!", count));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Found Failed!", NOT_FOUND));
        }
    }

    @GetMapping("/category/{categoryName}/product/{productName}")
    public ResponseEntity<StoreResponse> getProductsByCategoryAndProductName(@PathVariable String categoryName, @PathVariable String productName){
        try {
            List<Product> products = productService.getProductsByCategoryAndName(categoryName, productName);
            return ResponseEntity.ok(new StoreResponse("Found Success!", products));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Found Failed!", NOT_FOUND));
        }
    }
}
