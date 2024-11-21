package com.domain.store.services.order;

import com.domain.store.exception.FoundException;
import com.domain.store.model.Cart;
import com.domain.store.model.User;
import com.domain.store.model.Orders;
import com.domain.store.repository.ItemRepository;
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
    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public Orders placeOrder(Long userId) {
        User user = userService.getUser(userId);
        Cart cart = user.getCart();
        Orders order = new Orders();
        order.setOrderAmount(cart.getTotalAmount());
        order.setOrderStatus(OrderStatus.PLACED);
        order.setOrderDateTime(LocalDateTime.now());
        order.setUser(user);
        cart.getItems().forEach(item -> {
            item.setOrder(order);
            item.setCart(null);
            itemRepository.save(item);
        });
        return orderRepository.save(order);
    }

    @Override
    public Orders getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(()-> new FoundException("Order not found with Id: "+id));
    }

    @Override
    public Orders updateOrderStatus(Long id, OrderStatus status) {
        Orders order = getOrder(id);
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }
}
