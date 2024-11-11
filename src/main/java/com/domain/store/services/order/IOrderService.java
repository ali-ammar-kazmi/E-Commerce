package com.domain.store.services.order;

import com.domain.store.model.UserOrder;
import com.domain.store.services.OrderStatus;

public interface IOrderService {
    public UserOrder placeOrder(Long userId);
    public UserOrder getOrder(Long id);
    UserOrder updateOrderStatus(Long id, OrderStatus status);
}
