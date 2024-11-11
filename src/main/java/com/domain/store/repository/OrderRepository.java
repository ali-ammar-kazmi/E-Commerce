package com.domain.store.repository;

import com.domain.store.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {
}
