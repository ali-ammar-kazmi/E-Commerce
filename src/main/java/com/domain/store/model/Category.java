package com.domain.store.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy="category", cascade=CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Product> products;
}
