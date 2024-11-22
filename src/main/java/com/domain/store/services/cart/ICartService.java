package com.domain.store.services.cart;

import com.domain.store.model.Cart;
import com.domain.store.model.User;

public interface ICartService {
    public Cart addCart(User user);
    public Cart getCart(Long id);
}
