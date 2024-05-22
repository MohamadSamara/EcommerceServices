package com.samara.order.service.service;

import com.samara.order.service.bo.order.OrderResponse;


public interface OrderService {

    OrderResponse order(Long userId);

    OrderResponse createOrder(Long userId, Long cartId);

    String clearOrderIfExisting(Long userId);
}