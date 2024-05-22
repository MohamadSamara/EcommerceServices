package com.samara.order.service.mapper;


import com.samara.order.service.bo.cart.CartResponse;
import com.samara.order.service.bo.cartItem.CartItemResponse;
import com.samara.order.service.bo.order.OrderResponse;
import com.samara.order.service.bo.orderItem.OrderItemResponse;
import com.samara.order.service.bo.product.ProductResponse;
import com.samara.order.service.model.OrderEntity;
import com.samara.order.service.model.OrderItem;
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

    @Qualifier("cartWebClient")
    private final WebClient cartWebClient;

    public Mapper(WebClient productWebClient, WebClient cartWebClient) {
        this.productWebClient = productWebClient;
        this.cartWebClient = cartWebClient;
    }

    public OrderResponse orderEntityToOrderResponse(OrderEntity order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .deletedAt(order.getDeletedAt())
                .modifiedAt(order.getModifiedAt())
                .orderItem(order.getOrderItem()
                        .stream()
                        .map(this::orderEntityToOrderItemResponse)
                        .toList()
                ).build();
    }


    public OrderItemResponse orderEntityToOrderItemResponse(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .productId(getProduct(orderItem.getProductId()))
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }

    private ProductResponse getProduct(Long productId) {
        ResponseEntity<ProductResponse> entity = productWebClient
                .get()
                .uri(
                        uriBuilder -> uriBuilder.path("/{id}").build(productId)
                )
                .retrieve()
                .toEntity(ProductResponse.class)
                .block();
        if (entity != null && entity.hasBody()) {
            return entity.getBody();
        } else {
            throw new RuntimeException("Failed to get Product from order");
        }
    }


    public CartResponse getCart(Long userId) {
        ResponseEntity<CartResponse> entity = cartWebClient
                .get()
                .uri(
                        uriBuilder -> uriBuilder.path("/{userId}/cart-details").build(userId)
                )
                .retrieve()
                .toEntity(CartResponse.class)
                .block();
        if (entity != null && entity.hasBody()) {
            return entity.getBody();
        } else {
            throw new RuntimeException("Failed to get Product from order");
        }
    }

    public List<CartItemResponse> getCartItems(Long userId) {
        return getCart(userId).getCartItem();
    }

    public List<OrderItem> getOrderItemsFromCartItems(Long userId, OrderEntity cartId) {

        List<CartItemResponse> cartItems = getCartItems(userId);

        return cartItems.stream()
                .map(cartItem -> OrderItem.builder()
                        .quantity(cartItem.getQuantity())
                        .productId(cartItem.getProductId().getId())
                        .totalPrice(cartItem.getTotalPrice())
                        .order(cartId)
                        .build()
                ).collect(Collectors.toList());
    }

}