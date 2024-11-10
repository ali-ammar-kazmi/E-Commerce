package com.domain.store.services.order;

import com.domain.store.exception.FoundException;
import com.domain.store.model.Cart;
import com.domain.store.model.Order;
import com.domain.store.model.User;
import com.domain.store.repository.OrderRepository;
import com.domain.store.services.OrderStatus;
import com.domain.store.services.user.IUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Data
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final IUserService userService;

    @Override
    public Order placeOrder(Long userId) {
        User user = userService.getUser(userId);
        Cart cart = user.getCart();
        Order order = new Order();
        order.setOrderAmount(cart.getTotalAmount());
        order.setOrderStatus(OrderStatus.PLACED);
        order.setOrderDateTime(LocalDateTime.now());
        order.setUser(user);
        order.setCartItems(cart.getCartItems());
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(()-> new FoundException("Order not found with Id: "+id));
    }

    @Override
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrder(id);
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }
}
