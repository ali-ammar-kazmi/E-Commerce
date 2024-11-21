package com.domain.store.services.cart;

import com.domain.store.model.Item;


public interface ICartItemService {
    public Item addItemToCart(Long cartId, Long productId);
    public Item getCartItem(Long id);
    public Item updateQuantity(Long id, int quantity);
    public void deleteCartItem(Long id);
}
