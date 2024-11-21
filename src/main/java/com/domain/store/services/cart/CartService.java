package com.domain.store.services.cart;

import com.domain.store.exception.FoundException;
import com.domain.store.model.Cart;
import com.domain.store.model.Item;
import com.domain.store.model.User;
import com.domain.store.repository.ItemRepository;
import com.domain.store.repository.CartRepository;
import com.domain.store.repository.OrderRepository;
import com.domain.store.model.OrderStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Data
@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Override
    public Cart addOrderCart(User user) {
        Cart newCart = new Cart();
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
                        Set<Item> orderItems = order.getItems();
                        orderItems.forEach(itemRepository::delete);
                        orderRepository.save(order);
                    }
                }
        );
        return cartRepository.save(cart);
    }
}
