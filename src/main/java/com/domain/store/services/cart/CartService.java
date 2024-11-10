package com.domain.store.services.cart;

import com.domain.store.exception.FoundException;
import com.domain.store.model.Cart;
import com.domain.store.model.CartItem;
import com.domain.store.model.User;
import com.domain.store.repository.CartItemRepository;
import com.domain.store.repository.CartRepository;
import com.domain.store.repository.OrderRepository;
import com.domain.store.repository.UserRepository;
import com.domain.store.services.OrderStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Data
@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public Cart addOrderCart(Long userId) {
        Cart newCart = new Cart();
        User user = userRepository.findById(userId).orElseThrow(()-> new FoundException("User Not Found with id: " + userId));
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }

    @Override
    public Cart getOrderCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new FoundException("Cart Not Found with id: " + id));
        cart.setTotalAmount();
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public Cart clearCart(Long id) {
        Cart cart = getOrderCart(id);
        User user = cart.getUser();
        user.getOrders().forEach(
                order -> {
                    if (order.getOrderStatus().equals(OrderStatus.DELIVERED)){
                        Set<CartItem> orderCartItems = order.getCartItems();
                        orderCartItems.forEach(cartItemRepository::delete);
                        orderRepository.save(order);
                    }
                }
        );
        return cartRepository.save(cart);
    }
}
