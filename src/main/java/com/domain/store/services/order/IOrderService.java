package com.domain.store.services.order;

import com.domain.store.model.Orders;
import com.domain.store.model.OrderStatus;

public interface IOrderService {
    public Orders placeOrder(Long userId);
    public Orders getOrder(Long id);
    Orders updateOrderStatus(Long id, OrderStatus status);
}
