package com.ecommerce_system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderRequest {
    private Integer shippingContactMechId;
    private Integer billingContactMechId;
}
