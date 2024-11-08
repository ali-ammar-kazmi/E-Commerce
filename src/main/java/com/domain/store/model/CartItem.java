package com.domain.store.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigInteger quantity = BigInteger.ONE;
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Cart cart;

    @OneToOne(mappedBy= "cartItem", cascade= CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn
    private Product product;
}
