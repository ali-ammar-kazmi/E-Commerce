package com.domain.store.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private String categoryName;
}
