package com.domain.store.services.order;

import com.domain.store.exception.FoundException;
import com.domain.store.model.Cart;
import com.domain.store.model.User;
import com.domain.store.model.UserOrder;
import com.domain.store.repository.CartItemRepository;
import com.domain.store.repository.OrderRepository;
import com.domain.store.model.OrderStatus;
import com.domain.store.services.user.IUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Data
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final IUserService userService;
    private final CartItemRepository cartItemRepository;

    @Transactional
    @Override
    public UserOrder placeOrder(Long userId) {
        User user = userService.getUser(userId);
        Cart cart = user.getCart();
        UserOrder order = new UserOrder();
        order.setOrderAmount(cart.getTotalAmount());
        order.setOrderStatus(OrderStatus.PLACED);
        order.setOrderDateTime(LocalDateTime.now());
        order.setUser(user);
        cart.getCartItems().forEach(cartItem -> {
            cartItem.setOrder(order);
            cartItem.setCart(null);
            cartItemRepository.save(cartItem);
        });
        return orderRepository.save(order);
    }

    @Override
    public UserOrder getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(()-> new FoundException("Order not found with Id: "+id));
    }

    @Override
    public UserOrder updateOrderStatus(Long id, OrderStatus status) {
        UserOrder order = getOrder(id);
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }
}
