package com.samara.cart.service.bo.cartItem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCartItemRequest {
    private Long productId;
    private Integer quantity;
}