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
    public CartItem addItemToCart(Long cartId, Long productId) {
        try {
            if (cartItemRepository.existsByCartIdAndProductId(cartId, productId)){
                CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cartId, productId);
                cartItem.setQuantity(cartItem.getQuantity()+1);
                cartItem.setPrice();
                return cartItemRepository.save(cartItem);
            }
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cartService.getOrderCart(cartId));
            newCartItem.setProduct(productService.getProductById(productId));
            newCartItem.setPrice();
            return cartItemRepository.save(newCartItem);
        } catch (Exception e) {
            throw new FoundException(e.getMessage());
        }
    }

    @Override
    public CartItem getCartItem(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(()-> new FoundException("CartItem Not Found with id: " + id));
        cartItem.setPrice();
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateQuantity(Long id, int quantity) {
        CartItem cartItem = getCartItem(id);
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setPrice();
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.findById(id).ifPresentOrElse(cartItemRepository::delete, ()-> { throw new FoundException("CartItem Not Found with id: " + id);});
    }
}
