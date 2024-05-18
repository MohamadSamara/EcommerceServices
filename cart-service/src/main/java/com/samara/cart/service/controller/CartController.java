package com.samara.cart.service.controller;

import com.samara.cart.service.bo.cart.CartResponse;
import com.samara.cart.service.bo.cart.CreateCartRequest;
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


//    @GetMapping("/cart")
//    public ResponseEntity<List<CartResponse>> cart() {
//        return new ResponseEntity<>(cartService.cart(), HttpStatus.OK);
//    }

//    @GetMapping("/cart/{userId}")
//    public ResponseEntity<List<CartResponse>> userCart(@PathVariable Long userId) {
//        return new ResponseEntity<>(cartService.userCart(), HttpStatus.OK);
//    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<CartResponse> addToCart(@RequestBody CreateCartRequest createCartRequest) {
        return new ResponseEntity<>(cartService.addToCart(createCartRequest), HttpStatus.CREATED);
    }

    @GetMapping("/cart-details/{userId}")
    public ResponseEntity<List<CartResponse>> cartDetails(@PathVariable Long userId) {
        return new ResponseEntity<>(cartService.cartDetails(userId), HttpStatus.OK);
    }

    @GetMapping("/allMultiValueMap")
    public Map<String, String> allMultiValueMap(@RequestHeader MultiValueMap<String, String> headers) {
        return headers.toSingleValueMap();
    }

}