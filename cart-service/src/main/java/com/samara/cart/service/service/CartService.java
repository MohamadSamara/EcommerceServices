package com.samara.cart.service.service;

import com.samara.cart.service.bo.cart.CartResponse;
import com.samara.cart.service.bo.cart.CreateCartRequest;

import java.util.List;

public interface CartService {

    CartResponse addToCart(CreateCartRequest createCartRequest);

    List<CartResponse> cartDetails(Long userId);
}