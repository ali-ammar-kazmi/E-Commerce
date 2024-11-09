package com.domain.store.services.cart;

import com.domain.store.exception.FoundException;
import com.domain.store.model.Cart;
import com.domain.store.repository.CartItemRepository;
import com.domain.store.repository.CartRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Data
@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart addOrderCart() {
        Cart newCart = new Cart();
        return cartRepository.save(newCart);
    }

    @Override
    public Cart getOrderCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new FoundException("Cart Not Found with id: " + id));
        cart.setTotalAmount();
        return cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.findById(id).ifPresentOrElse(cartRepository::delete, ()-> {throw new FoundException("Cart Not Found with id: " + id);});
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getOrderCart(id);
        cart.getCartItems().forEach(cartItemRepository::delete);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}
