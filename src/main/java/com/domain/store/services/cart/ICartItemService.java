package com.domain.store.services.cart;

import com.domain.store.model.CartItem;


public interface ICartItemService {
    public CartItem addItemToCart(Long cartId, Long productId);
    public CartItem getCartItem(Long id);
    public CartItem updateQuantity(Long id, int quantity);
    public void deleteCartItem(Long id);
}
