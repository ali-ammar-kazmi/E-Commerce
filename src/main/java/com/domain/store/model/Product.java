package com.domain.store.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Category category;

    @OneToMany(mappedBy="product", cascade=CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ImageConfig> images;

    @OneToMany(mappedBy = "product", cascade=CascadeType.ALL)
    @JsonBackReference
    private List<CartItem> cartItem;
}
