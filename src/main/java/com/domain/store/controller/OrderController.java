package com.domain.store.controller;

import com.domain.store.exception.StoreException;
import com.domain.store.model.Orders;
import com.domain.store.response.StoreResponse;
import com.domain.store.model.OrderStatus;
import com.domain.store.services.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/order")
public class OrderController {
    private final IOrderService orderService;

    @GetMapping("/save/{userId}")
    public ResponseEntity<StoreResponse> placeOrder(@PathVariable Long userId){
        try{
            Orders order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new StoreResponse("Order Placed!", order));
        } catch ( Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new StoreResponse("Order Placed Failed!", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/retrieveById/{orderId}")
    public ResponseEntity<StoreResponse> getOrder(@PathVariable Long orderId){
        try{
            Orders order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new StoreResponse("Order Found!", order));
        } catch ( StoreException e){
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Order Not Found!", NOT_FOUND));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new StoreResponse("Order Not Found!", INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/update/{orderId}/{orderStatus}")
    public ResponseEntity<StoreResponse> updateCategory(@PathVariable Long orderId, @PathVariable OrderStatus orderStatus){
        try{
            Orders order = orderService.updateOrderStatus(orderId, orderStatus);
            return ResponseEntity.ok(new StoreResponse("Order Updated!", order));
        } catch ( StoreException e){
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("Order Not Updated!", NOT_FOUND));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new StoreResponse("Order Not Updated!", INTERNAL_SERVER_ERROR));
        }
    }

}
