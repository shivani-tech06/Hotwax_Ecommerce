package com.ecommerce_system.repository;

import com.ecommerce_system.entity.Order_Header;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHeaderRepository extends JpaRepository<Order_Header, Integer> {}
