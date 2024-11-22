package com.domain.store.services.item;

import com.domain.store.model.Item;

public interface IItemService {
    Item addItemToCart(Long cartId, Long productId);
    Item getCartItem(Long id);
    Item updateQuantity(Long id, int quantity);
    void deleteCartItem(Long id);
}
