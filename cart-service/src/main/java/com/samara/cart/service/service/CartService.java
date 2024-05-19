package com.samara.cart.service.service;

import com.samara.cart.service.bo.cart.CartResponse;
import com.samara.cart.service.bo.cart.CreateCartRequest;
import com.samara.cart.service.bo.cart.UpdateCartRequest;

import java.util.List;

public interface CartService {

    CartResponse addToCart(CreateCartRequest createCartRequest, Long userId);

    List<CartResponse> cartDetails(Long userId);

    String deleteCart(Long userId, Long productId);

    CartResponse updateCartQuantity(Long userId, Long productId, UpdateCartRequest updateCartRequest);
}