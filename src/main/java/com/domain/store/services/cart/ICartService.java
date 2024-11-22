package com.domain.store.services.cart;

import com.domain.store.model.Cart;
import com.domain.store.model.User;

public interface ICartService {
    Cart addCart(User user);
    Cart getCart(Long id);
}
