package com.ecommerce_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "order_header")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Order_Header {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_contact_mech_id", nullable = false)
    private Contact_Mech shippingContactMech;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_contact_mech_id", nullable = false)
    private Contact_Mech billingContactMech;
}
