package com.domain.store.services.cart;

import com.domain.store.model.Cart;

public interface ICartService {
    public Cart addOrderCart(Long userId);
    public Cart getOrderCart(Long id);
    public Cart clearCart(Long id);
}
