package com.samara.cart.service.bo.cartItem;

import com.samara.cart.service.bo.product.ProductResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponse {
    private Long id;
    private ProductResponse productId;
    private Integer quantity;
    private Double totalPrice;
}