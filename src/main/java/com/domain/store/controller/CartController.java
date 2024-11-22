package com.domain.store.controller;

import com.domain.store.model.Cart;
import com.domain.store.model.Item;
import com.domain.store.response.StoreResponse;
import com.domain.store.services.item.ItemService;
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
    private final ItemService itemService;

    @GetMapping("/retrieveCart/{cartId}")
    public ResponseEntity<StoreResponse> getCart(@PathVariable Long cartId){
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new StoreResponse("Found success!", cart));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Found Failed!", NOT_FOUND));
        }
    }

    @GetMapping("/saveItem/cartId/{cartId}/productId/{productId}")
    public ResponseEntity<StoreResponse> addCartItem(@PathVariable Long cartId, @PathVariable Long productId){
        try {
            Item item = itemService.addItemToCart(cartId, productId);
            return ResponseEntity.ok(new StoreResponse("Save success!", item));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new StoreResponse("Save Failed!", INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/update/cartItem/{cartItemId}/quantity/{quantity}")
    public ResponseEntity<StoreResponse> updateCartItem(@PathVariable Long cartItemId, @PathVariable int quantity){
        try {
            Item item = itemService.updateQuantity(cartItemId, quantity);
            return ResponseEntity.ok(new StoreResponse("Update success!", item));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Update Failed!", NOT_FOUND));
        }
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<StoreResponse> deleteCartItem(@PathVariable Long cartItemId){
        try {
            itemService.deleteCartItem(cartItemId);
            return ResponseEntity.ok(new StoreResponse("Delete success!", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Delete Failed!", NOT_FOUND));
        }
    }
}
