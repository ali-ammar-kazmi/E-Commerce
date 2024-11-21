package com.domain.store.controller;

import com.domain.store.exception.FoundException;
import com.domain.store.model.Order;
import com.domain.store.response.ApiResponse;
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
    public ResponseEntity<ApiResponse> placeOrder(@PathVariable Long userId){
        try{
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Order Placed!", order));
        } catch ( Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Order Placed Failed!", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/retrieveById/{orderId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long orderId){
        try{
            Order order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Order Found!", order));
        } catch ( FoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Order Not Found!", NOT_FOUND));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Order Not Found!", INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/update/{orderId}/{orderStatus}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable OrderStatus orderStatus, @PathVariable Long orderId){
        try{
            Order order = orderService.updateOrderStatus(orderId, orderStatus);
            return ResponseEntity.ok(new ApiResponse("Order Updated!", order));
        } catch ( FoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Order Not Updated!", NOT_FOUND));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Order Not Updated!", INTERNAL_SERVER_ERROR));
        }
    }

}
