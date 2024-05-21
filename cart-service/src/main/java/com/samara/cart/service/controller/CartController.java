package com.samara.cart.service.controller;

import com.samara.cart.service.bo.cart.CartResponse;
import com.samara.cart.service.bo.cart.UpdateCartRequest;
import com.samara.cart.service.bo.cartItem.CreateCartItemRequest;
import com.samara.cart.service.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}/cart-details")
    public ResponseEntity<CartResponse> cartDetails(@PathVariable Long userId) {
        return new ResponseEntity<>(cartService.cartDetails(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/add-to-cart")
    public ResponseEntity<CartResponse> addToCart(
            @RequestBody CreateCartItemRequest createCartRequest,
            @PathVariable Long userId
    ) {
        return new ResponseEntity<>(cartService.addToCart(createCartRequest, userId), HttpStatus.CREATED);
    }


    @DeleteMapping("/{userId}/delete-cart-item/{productId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long userId, @PathVariable Long productId) {
        return new ResponseEntity<>(cartService.deleteCartItem(userId, productId), HttpStatus.OK);
    }

    @PutMapping("/{userId}/update-quantity/{productId}")
    public ResponseEntity<String> updateCartQuantity(
            @RequestBody UpdateCartRequest updateCartRequest,
            @PathVariable Long userId,
            @PathVariable Long productId
    ) {
        return new ResponseEntity<>(cartService.updateCartQuantity(updateCartRequest, userId, productId), HttpStatus.CREATED);
    }


}