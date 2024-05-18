package com.samara.cart.service.bo.cart;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateCartRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private LocalDateTime createdAt;

}