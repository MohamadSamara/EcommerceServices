package com.samara.cart.service.mapper;

import com.samara.cart.service.bo.cart.CartResponse;
import com.samara.cart.service.bo.cartItem.CartItemResponse;
import com.samara.cart.service.bo.cartItem.CreateCartItemRequest;
import com.samara.cart.service.bo.product.ProductResponse;
import com.samara.cart.service.model.CartEntity;
import com.samara.cart.service.model.CartItem;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {
    @Qualifier("productWebClient")
    private final WebClient productWebClient;

    public Mapper(WebClient productWebClient) {
        this.productWebClient = productWebClient;
    }

    public CartResponse EntityToCartResponse(CartEntity cartEntity) {

        List<CartItemResponse> cartItemResponses = cartEntity.getCartItem()
                .stream()
                .map(this::cartItemEntityToCartItemResponse)
                .collect(Collectors.toList());

        return CartResponse.builder()
                .id(cartEntity.getId())
                .userId(cartEntity.getUserId())
                .cartItem(cartItemResponses)
                .createdAt(cartEntity.getCreatedAt())
                .totalPrice(cartEntity.getTotalPrice())
                .modifiedAt(cartEntity.getModifiedAt())
                .build();
    }


    public CartItemResponse cartItemEntityToCartItemResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .productId(getProduct(cartItem.getProductId()))
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getTotalPrice())
                .build();
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


    public CartItem createCartItemRequestToCartItemEntity(CreateCartItemRequest createCartItemRequest, CartEntity cart) {
        ProductResponse productResponse = getProduct(createCartItemRequest.getProductId());
        return CartItem.builder()
                .cartId(cart)
                .quantity(createCartItemRequest.getQuantity())
                .productId(productResponse.getId())
                .totalPrice(createCartItemRequest.getQuantity() * productResponse.getSalePrice())
                .build();
    }


}