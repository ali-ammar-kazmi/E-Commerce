package com.domain.store.services.cart;

import com.domain.store.exception.FoundException;
import com.domain.store.model.Cart;
import com.domain.store.repository.CartRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Data
@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final CartRepository cartRepository;

    @Override
    public Cart addOrderCart() {
        Cart newCart = new Cart();
        return cartRepository.save(newCart);
    }

    @Override
    public Cart getOrderCart(Long id) {
        return cartRepository.findById(id).orElseThrow(()-> new FoundException("Cart Not Found with id: " + id));
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        try {
            return getOrderCart(id).getTotalAmount();
        } catch (FoundException e) {
            throw new FoundException(e.getMessage());
        }
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.findById(id).ifPresentOrElse(cartRepository::delete, ()-> {throw new FoundException("Cart Not Found with id: " + id);});
    }

    @Override
    public void clearCart(Long id) {
    }
}
