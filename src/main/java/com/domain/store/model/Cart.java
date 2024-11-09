package com.domain.store.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy= "cart", cascade= CascadeType.ALL, orphanRemoval= true)
    @JsonManagedReference
    private Set<CartItem> cartItems;

    public Cart setTotalAmount(){
        this.totalAmount = this.cartItems.stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO,BigDecimal::add);
        return this;
    }
}
