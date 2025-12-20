package com.ecommerce_system.service;

import com.ecommerce_system.dto.CreateOrderItemRequest;
import com.ecommerce_system.dto.UpdateOrderItemRequest;
import com.ecommerce_system.entity.*;
import com.ecommerce_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderHeaderRepository orderHeaderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    // POST /orders/{orderId}/items
    @Transactional
    public Integer addOrderItem(Integer orderId, CreateOrderItemRequest request) {

        Order_Header order = orderHeaderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Order_Item item = new Order_Item();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(request.getQuantity());
        item.setStatus(request.getStatus());

        return orderItemRepository.save(item).getOrderItemSeqId();
    }

    // PUT /orders/{orderId}/items/{itemId}
    @Transactional
    public void updateOrderItem(Integer orderId, Integer itemId, UpdateOrderItemRequest request) {

        Order_Item item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        if (!item.getOrder().getOrderId().equals(orderId)) {
            throw new RuntimeException("Item is not in this order");
        }

        if (request.getQuantity() != null) item.setQuantity(request.getQuantity());
        if (request.getStatus() != null) item.setStatus(request.getStatus());

        orderItemRepository.save(item);
    }

    // DELETE /orders/{orderId}/items/{itemId}
    @Transactional
    public void deleteOrderItem(Integer orderId, Integer itemId) {

        Order_Item item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        if (!item.getOrder().getOrderId().equals(orderId)) {
            throw new RuntimeException("Item is not in this order");
        }

        orderItemRepository.delete(item);
    }
}
