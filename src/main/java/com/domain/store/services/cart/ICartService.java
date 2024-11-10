package com.domain.store.services.cart;

import com.domain.store.model.Cart;
import com.domain.store.model.User;

public interface ICartService {
    public Cart addOrderCart(User user);
    public Cart getOrderCart(Long id);
    public Cart clearCart(Long id);
}
