package com.domain.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private List<ProductDto> products;
}
