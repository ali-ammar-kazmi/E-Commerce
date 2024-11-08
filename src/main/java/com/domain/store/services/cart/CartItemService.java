package com.domain.store.services.cart;

import com.domain.store.exception.FoundException;
import com.domain.store.model.CartItem;
import com.domain.store.repository.CartItemRepository;
import com.domain.store.services.product.ProductService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final ProductService productService;

    @Override
    public CartItem addCartItem(Long cartId, Long productId) {
        CartItem newCartItem = new CartItem();
        newCartItem.setCart(cartService.getOrderCart(cartId));
        newCartItem.setProduct(productService.getProductById(productId));
        newCartItem.setUnitPrice();
        newCartItem.setTotalPrice();
        return cartItemRepository.save(newCartItem);
    }

    @Override
    public CartItem getCartItem(Long id) {
        return cartItemRepository.findById(id).orElseThrow(()-> new FoundException("CartItem Not Found with id: " + id));
    }

    @Override
    public CartItem updateQuantity(Long id, int quantity) {
        return null;
//        return cartItemRepository.findById(id).map((cartItem)->{
//            int oldQuantity = cartItem.getQuantity();
//            cartItem.setQuantity(oldQuantity + quantity);
//            return cartItemRepository.save(oldQuantity);
//        }).orElseThrow(), ()-> {throw new FoundException("CartItem Not Found with id: " + id);});
    }

    @Override
    public void deleteCartItem(Long id) {

    }
}
