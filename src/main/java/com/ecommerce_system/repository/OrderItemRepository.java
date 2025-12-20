package com.ecommerce_system.repository;

import com.ecommerce_system.entity.Order_Item;   
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OrderItemRepository extends JpaRepository<Order_Item, Integer> {

    @Transactional
    void deleteByOrder_OrderId(Integer orderId);
}
