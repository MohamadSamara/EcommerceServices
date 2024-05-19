package com.samara.cart.service.controller;

import com.samara.cart.service.bo.cart.CartResponse;
import com.samara.cart.service.bo.cart.CreateCartRequest;
import com.samara.cart.service.bo.cart.UpdateCartRequest;
import com.samara.cart.service.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping("/{userId}/add-to-cart")
    public ResponseEntity<CartResponse> addToCart(
            @RequestBody CreateCartRequest createCartRequest,
            @PathVariable Long userId
    ) {
        return new ResponseEntity<>(cartService.addToCart(createCartRequest, userId), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/cart-details")
    public ResponseEntity<List<CartResponse>> cartDetails(@PathVariable Long userId) {
        return new ResponseEntity<>(cartService.cartDetails(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/delete-cart/{productId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long userId, @PathVariable Long productId) {
        return new ResponseEntity<>(cartService.deleteCart(userId, productId), HttpStatus.OK);
    }

    @PutMapping("/{userId}/update-cart/{productId}")
    public ResponseEntity<CartResponse> updateCartQuantity(@PathVariable Long userId, @PathVariable Long productId, @RequestBody UpdateCartRequest updateCartRequest) {
        return new ResponseEntity<>(cartService.updateCartQuantity(userId, productId, updateCartRequest), HttpStatus.OK);
    }

    @GetMapping("/allMultiValueMap")
    public Map<String, String> allMultiValueMap(@RequestHeader MultiValueMap<String, String> headers) {
        return headers.toSingleValueMap();
    }

}