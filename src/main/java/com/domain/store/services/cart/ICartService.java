package com.domain.store.services.cart;

import com.domain.store.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    public Cart addOrderCart();
    public Cart getOrderCart(Long id);
    public void deleteCart(Long id);
    public void clearCart(Long id);
}
