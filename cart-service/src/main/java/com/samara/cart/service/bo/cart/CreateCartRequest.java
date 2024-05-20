package com.samara.cart.service.bo.cart;

import com.samara.cart.service.model.CartItem;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CreateCartRequest {
    private Long userId;
    private List<CartItem> cartItem;
    private Double totalPrice;
    private LocalDateTime createdAt;
}