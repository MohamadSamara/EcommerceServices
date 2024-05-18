package com.samara.cart.service.bo.cart;

import com.samara.cart.service.bo.product.ProductResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CartResponse {
    private Long id;
    private Long userId;
    private ProductResponse productId;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}