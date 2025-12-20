package com.ecommerce_system.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateOrderItemRequest {
    private Integer quantity;
    private String status;
}
