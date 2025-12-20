package com.ecommerce_system.service;

import com.ecommerce_system.dto.CreateOrderItemRequest;
import com.ecommerce_system.dto.CreateOrderRequest;
import com.ecommerce_system.dto.UpdateOrderRequest;
import com.ecommerce_system.entity.*;
import com.ecommerce_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerRepository customerRepository;
    private final ContactMechRepository contactMechRepository;
    private final ProductRepository productRepository;
    private final OrderHeaderRepository orderHeaderRepository;
    private final OrderItemRepository orderItemRepository;

    // POST /orders
    @Transactional
    public Integer createOrder(CreateOrderRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Contact_Mech shipping = contactMechRepository.findById(request.getShippingContactMechId())
                .orElseThrow(() -> new RuntimeException("Shipping contact not found"));

        Contact_Mech billing = contactMechRepository.findById(request.getBillingContactMechId())
                .orElseThrow(() -> new RuntimeException("Billing contact not found"));

        Order_Header order = new Order_Header();
        order.setOrderDate(request.getOrderDate());
        order.setCustomer(customer);
        order.setShippingContactMech(shipping);
        order.setBillingContactMech(billing);

        Order_Header savedOrder = orderHeaderRepository.save(order);

        if (request.getOrderItems() != null) {
            for (CreateOrderItemRequest itemReq : request.getOrderItems()) {

                Product product = productRepository.findById(itemReq.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                Order_Item item = new Order_Item();
                item.setOrder(savedOrder);
                item.setProduct(product);
                item.setQuantity(itemReq.getQuantity());
                item.setStatus(itemReq.getStatus());

                orderItemRepository.save(item);
            }
        }

        return savedOrder.getOrderId();
    }

    // PUT /orders/{orderId}
    @Transactional
    public void updateOrder(Integer orderId, UpdateOrderRequest request) {

        Order_Header order = orderHeaderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (request.getShippingContactMechId() != null) {
            Contact_Mech shipping = contactMechRepository.findById(request.getShippingContactMechId())
                    .orElseThrow(() -> new RuntimeException("Shipping contact not found"));
            order.setShippingContactMech(shipping);
        }

        if (request.getBillingContactMechId() != null) {
            Contact_Mech billing = contactMechRepository.findById(request.getBillingContactMechId())
                    .orElseThrow(() -> new RuntimeException("Billing contact not found"));
            order.setBillingContactMech(billing);
        }

        orderHeaderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Integer orderId) {

        // 1) Pehle child delete
        orderItemRepository.deleteByOrder_OrderId(orderId);

        // 2) Fir parent delete
        orderHeaderRepository.deleteById(orderId);
    }

}
