package com.samara.order.service.service;

import com.samara.order.service.bo.order.OrderResponse;


public interface OrderService {

    OrderResponse order(Long orderId);

    OrderResponse orderForUser(Long userId);

    String deleteOrderForUser(Long userId);
}