package com.samara.cart.service.service;

import com.samara.cart.service.bo.cart.CartResponse;
import com.samara.cart.service.bo.cartItem.CreateCartItemRequest;

public interface CartService {

    CartResponse addToCart(CreateCartItemRequest createCartItemRequest, Long userId);

    CartResponse cartDetails(Long userId);

    String deleteCart(Long userId);

    String deleteCartItem(Long userId, Long productId);

//    CartResponse updateCartQuantity(Long userId, Long productId, UpdateCartRequest updateCartRequest);
}