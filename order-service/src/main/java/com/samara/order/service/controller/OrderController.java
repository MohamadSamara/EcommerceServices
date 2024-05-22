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

    @GetMapping("/user-order/{userId}")
    public ResponseEntity<OrderResponse> order(@PathVariable Long userId) {
        return new ResponseEntity<>(orderService.order(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/create-order/{cartId}")
    public ResponseEntity<OrderResponse> createOrder(
            @PathVariable Long userId,
            @PathVariable Long cartId
    ) {
        return new ResponseEntity<>(orderService.createOrder(userId, cartId), HttpStatus.OK);
    }

    @DeleteMapping("/delete-order/{userId}")
    public ResponseEntity<String> clearExistingOrder(@PathVariable Long userId) {
        return new ResponseEntity<>(orderService.clearOrderIfExisting(userId), HttpStatus.OK);
    }


}