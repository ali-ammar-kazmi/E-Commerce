package com.domain.store.controller;

import com.domain.store.model.Cart;
import com.domain.store.model.Item;
import com.domain.store.response.ApiResponse;
import com.domain.store.services.cart.CartItemService;
import com.domain.store.services.cart.CartService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;

    @GetMapping("/retrieve/{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
        try {
            Cart cart = cartService.getOrderCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Found success!", cart));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Found Failed!", NOT_FOUND));
        }
    }

    @DeleteMapping("/clear/{cartId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
        try {
            Cart cart = cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear success!", cart));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Clear Failed!", NOT_FOUND));
        }
    }

    @GetMapping("/retrieveItem/{cartItemId}")
    public ResponseEntity<ApiResponse> getCartItem(@PathVariable Long cartItemId){
        try {
            Item item = cartItemService.getCartItem(cartItemId);
            return ResponseEntity.ok(new ApiResponse("Found success!", item));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Found Failed!", NOT_FOUND));
        }
    }

    @GetMapping("/saveItem/cartId/{cartId}/productId/{productId}")
    public ResponseEntity<ApiResponse> addCartItem(@PathVariable Long cartId, @PathVariable Long productId){
        try {
            Item item = cartItemService.addItemToCart(cartId, productId);
            return ResponseEntity.ok(new ApiResponse("Save success!", item));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Save Failed!", INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/update/cartItem/{cartItemId}/quantity/{quantity}")
    public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Long cartItemId, @PathVariable int quantity){
        try {
            Item item = cartItemService.updateQuantity(cartItemId, quantity);
            return ResponseEntity.ok(new ApiResponse("Update success!", item));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Update Failed!", NOT_FOUND));
        }
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId){
        try {
            cartItemService.deleteCartItem(cartItemId);
            return ResponseEntity.ok(new ApiResponse("Delete success!", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Delete Failed!", NOT_FOUND));
        }
    }
}
