package com.samara.cart.service.mapper;

import com.samara.cart.service.bo.cart.CartResponse;
import com.samara.cart.service.bo.cart.CreateCartRequest;
import com.samara.cart.service.bo.cartItem.CartItemResponse;
import com.samara.cart.service.bo.cartItem.CreateCartItemRequest;
import com.samara.cart.service.bo.product.ProductResponse;
import com.samara.cart.service.model.CartEntity;
import com.samara.cart.service.model.CartItem;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {
    @Qualifier("productWebClient")
    private final WebClient productWebClient;

    public Mapper(WebClient productWebClient) {
        this.productWebClient = productWebClient;
    }

    public CartResponse EntityToCartResponse(CartEntity cartEntity) {

        // Create Empty List To Avoid The Null Exception
        List<CartItemResponse> cartItemResponses = new ArrayList<>();
        // if the list not null (not empty) :: get all cartItemResponses
        if (cartEntity.getCartItem() != null) {
            cartItemResponses = cartEntity.getCartItem()
                    .stream()
                    .map(this::cartItemEntityToCartItemResponse)
                    .toList();
        }

        CartResponse cartResponse = CartResponse.builder()
                .id(cartEntity.getId())
                .userId(cartEntity.getUserId())
                .cartItem(cartItemResponses) // If it's null (empty) return empty list (note : empty list not like null)
                .createdAt(cartEntity.getCreatedAt())
                .modifiedAt(cartEntity.getModifiedAt())
                .build();

        cartResponse.setTotalPrice((calculateTotalPriceForCart(cartResponse)));
        return cartResponse;

    }


    public CartItemResponse cartItemEntityToCartItemResponse(CartItem cartItem) {
        CartItemResponse cartItemResponse = CartItemResponse.builder()
                .id(cartItem.getId())
                .productId(getProduct(cartItem.getProductId()))
                .quantity(cartItem.getQuantity())
                .build();

        cartItemResponse.setTotalPrice(calculateTotalPriceForCartIem(cartItemResponse));
        return cartItemResponse;
    }

    private Double calculateTotalPriceForCartIem(CartItemResponse cartItemResponse) {
        return cartItemResponse.getProductId().getPrice() * cartItemResponse.getQuantity();
    }

    private Double calculateTotalPriceForCart(CartResponse cartResponse) {

        return cartResponse.getCartItem()
                .stream()
                .mapToDouble(CartItemResponse::getTotalPrice)
                .sum();
    }


    public ProductResponse getProduct(Long id) {

        ResponseEntity<ProductResponse> productResponse =
                productWebClient.get()
                        .uri(
                                uri -> uri
                                        .path("/{id}")
                                        .build(id)
                        )
                        .retrieve()
                        .toEntity(ProductResponse.class)
                        .block();
        if (productResponse != null) {
            return productResponse.getBody();
        } else {
            throw new RuntimeException("Something Wrong in productResponse ...");
        }
    }

    public CartEntity CartRequestToEntity(CreateCartRequest createCartRequest) {
        return CartEntity.builder()
                .userId(createCartRequest.getUserId())
                .totalPrice(createCartRequest.getTotalPrice())
                .cartItem(createCartRequest.getCartItem())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public CartItem createCartItemRequestToCartItemEntity(CreateCartItemRequest createCartItemRequest, CartEntity cart) {

        ProductResponse productResponse = getProduct(createCartItemRequest.getProductId());

        return CartItem.builder()
                .cartId(cart)
                .quantity(createCartItemRequest.getQuantity())
                .productId(productResponse.getId())
                .totalPrice(createCartItemRequest.getQuantity() * productResponse.getPrice())
                .build();
    }


}