package com.domain.store.services.order;

import com.domain.store.model.Orders;
import com.domain.store.model.OrderStatus;

public interface IOrderService {
    Orders placeOrder(Long userId);
    Orders getOrder(Long id);
    Orders updateOrderStatus(Long id, OrderStatus status);
}
