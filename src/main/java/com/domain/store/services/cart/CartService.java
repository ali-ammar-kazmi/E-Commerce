package com.domain.store.services.cart;

import com.domain.store.exception.StoreException;
import com.domain.store.model.Cart;
import com.domain.store.model.User;
import com.domain.store.repository.ItemRepository;
import com.domain.store.repository.CartRepository;
import com.domain.store.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Data
@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Override
    public Cart addCart(User user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new StoreException("Cart Not Found with id: " + id));
        cart.setTotalAmount();
        return cartRepository.save(cart);
    }
}
