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
    private int quantity=1;
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JoinColumn
    @JsonManagedReference
    private Product product;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private UserOrder order;

    public void setPrice() {
        this.unitPrice = this.product.getPrice();
    }

    public BigDecimal getTotalPrice() {
        setPrice();
        return this.unitPrice.multiply(new BigDecimal(this.quantity));
    }
}
