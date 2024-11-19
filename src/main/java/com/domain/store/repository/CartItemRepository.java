package com.domain.store.repository;

import com.domain.store.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndProductId(Long cartId, Long productId);

    boolean existsByCartIdAndProductId(Long cartId, Long productId);
}
