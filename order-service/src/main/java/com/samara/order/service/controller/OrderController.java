package com.samara.order.service.controller;

import com.samara.order.service.bo.order.OrderResponse;
import com.samara.order.service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> order(@PathVariable Long orderId) {
        return new ResponseEntity<>(orderService.order(orderId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/user-order")
    public ResponseEntity<OrderResponse> orderForUser(@PathVariable Long userId) {
        return new ResponseEntity<>(orderService.orderForUser(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/delete-order")
    public ResponseEntity<String> deleteOrderForUser(@PathVariable Long userId) {
        return new ResponseEntity<>(orderService.deleteOrderForUser(userId), HttpStatus.OK);
    }


}