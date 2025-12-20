package com.ecommerce_system.controller;

import com.ecommerce_system.dto.*;
import com.ecommerce_system.service.OrderItemService;
import com.ecommerce_system.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

  
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
        Integer orderId = orderService.createOrder(request);
        return ResponseEntity
                .status(201)
                .body(Map.of("orderId", orderId));
    }

    
    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrder(
            @PathVariable Integer orderId,
            @RequestBody UpdateOrderRequest request
    ) {
        orderService.updateOrder(orderId, request);
        return ResponseEntity.ok(
                Map.of("message", "Order updated successfully")
        );
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok(
                Map.of("message", "Order deleted successfully")
        );
    }

   

    @PostMapping("/{orderId}/items")
    public ResponseEntity<?> addOrderItem(
            @PathVariable Integer orderId,
            @RequestBody CreateOrderItemRequest request
    ) {
        Integer itemId = orderItemService.addOrderItem(orderId, request);
        return ResponseEntity
                .status(201)
                .body(Map.of("orderItemSeqId", itemId));
    }

  
    @PutMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<?> updateOrderItem(
            @PathVariable Integer orderId,
            @PathVariable Integer itemId,
            @RequestBody UpdateOrderItemRequest request
    ) {
        orderItemService.updateOrderItem(orderId, itemId, request);
        return ResponseEntity.ok(
                Map.of("message", "Order item updated successfully")
        );
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<?> deleteOrderItem(
            @PathVariable Integer orderId,
            @PathVariable Integer itemId
    ) {
        orderItemService.deleteOrderItem(orderId, itemId);
        return ResponseEntity.ok(
                Map.of("message", "Order item deleted successfully")
        );
    }
}
