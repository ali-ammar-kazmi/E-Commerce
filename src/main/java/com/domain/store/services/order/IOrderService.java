package com.domain.store.services.order;

import com.domain.store.model.Order;
import com.domain.store.model.OrderStatus;

public interface IOrderService {
    public Order placeOrder(Long userId);
    public Order getOrder(Long id);
    Order updateOrderStatus(Long id, OrderStatus status);
}
