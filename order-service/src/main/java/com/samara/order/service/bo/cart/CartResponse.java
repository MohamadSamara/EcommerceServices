package com.samara.order.service.bo.cart;

import com.samara.order.service.bo.cartItem.CartItemResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CartResponse {
    private Long id;
    private Long userId;
    private List<CartItemResponse> cartItem;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}