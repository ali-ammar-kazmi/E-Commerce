package com.domain.store.services.order;

import com.domain.store.model.Order;

public interface IOrderService {
    public Order orderPlaced(Long userId);
    public Order getOrder(Long id);
}
