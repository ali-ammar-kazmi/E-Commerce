package com.domain.store.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn
    private Product product;

    public void setUnitPrice() {
        this.unitPrice = this.product.getPrice();
    }

    public void setTotalPrice() {
        this.totalPrice = this.unitPrice.multiply(new BigDecimal(this.quantity));
    }
}
