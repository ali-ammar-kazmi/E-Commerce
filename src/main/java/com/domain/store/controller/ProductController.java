package com.domain.store.controller;

import com.domain.store.model.Product;
import com.domain.store.request.ProductRequest;
import com.domain.store.response.ApiResponse;
import com.domain.store.services.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/product")
public class ProductController {
    private final IProductService productService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductRequest productRequest){
        try {
            Product product = productService.addProduct(productRequest);
            return ResponseEntity.ok(new ApiResponse("Save Success!", product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Save Failed!", e.getMessage()));
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable Long productId){
        try {
            Product product = productService.updateProduct(productRequest, productId);
            return ResponseEntity.ok(new ApiResponse("Update Success!", product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Failed!", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new ApiResponse("Delete Success!", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete Failed!", e.getMessage()));
        }
    }

    @GetMapping("/retrieve/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Found Success!", product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Found Failed!", e.getMessage()));
        }
    }

    @GetMapping("/retrieve/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("Found Success!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Found Failed!", e.getMessage()));
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String categoryName){
        try {
            List<Product> products = productService.getProductsByCategory(categoryName);
            return ResponseEntity.ok(new ApiResponse("Found Success!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Found Failed!", e.getMessage()));
        }
    }

    @GetMapping("/brand/{brandName}")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brandName){
        System.out.println(brandName);
        try {
            List<Product> products = productService.getProductsByBrand(brandName);
            return ResponseEntity.ok(new ApiResponse("Found Success!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Found Failed!", e.getMessage()));
        }
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String productName){
        try {
            List<Product> products = productService.getProductsByName(productName);
            return ResponseEntity.ok(new ApiResponse("Found Success!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Found Failed!", e.getMessage()));
        }
    }

    @GetMapping("/category/{categoryName}/brand/{brandName}")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@PathVariable String categoryName, @PathVariable String brandName){
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brandName);
            return ResponseEntity.ok(new ApiResponse("Found Success!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Found Failed!", e.getMessage()));
        }
    }

    @GetMapping("/brand/{brandName}/name/{productName}")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@PathVariable String brandName, @PathVariable String productName){
        try {
            int count = productService.countProductsByBrandAndName(brandName, productName);
            return ResponseEntity.ok(new ApiResponse("Found Success!", count));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Found Failed!", e.getMessage()));
        }
    }
}
