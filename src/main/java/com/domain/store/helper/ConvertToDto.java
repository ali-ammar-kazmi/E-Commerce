package com.domain.store.helper;

import com.domain.store.dto.CategoryDto;
import com.domain.store.dto.ImageDto;
import com.domain.store.dto.ProductDto;
import com.domain.store.model.Category;
import com.domain.store.model.Product;
import com.domain.store.repository.ImageRepository;
import com.domain.store.services.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class ConvertToDto {
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    private final IProductService productService;

    public List<ProductDto> convertProductToDtos(List<Product> products){
        return products.stream().map(this::convertProductToDto).toList();
    }

    public ProductDto convertProductToDto(Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<ImageDto> images = imageRepository.findAllByProductId(product.getId()).stream()
                .map(image-> modelMapper.map(image, ImageDto.class)).toList();
        productDto.setImages(images);
        productDto.setCategory(product.getCategory().getName());
        return productDto;
    }

    public List<CategoryDto> convertCategoryToDtos(List<Category> categories){
        return categories.stream().map((this::convertCategoryToDto)).toList();
    }

    public CategoryDto convertCategoryToDto(Category category){
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        List<ProductDto> products = this.convertProductToDtos(category.getProducts());
        categoryDto.setProducts(products);
        return categoryDto;
    }
}
