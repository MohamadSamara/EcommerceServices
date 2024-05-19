package com.samara.order.service.mapper;


import com.samara.order.service.bo.order.OrderResponse;
import com.samara.order.service.bo.orderItem.OrderItemResponse;
import com.samara.order.service.bo.product.ProductResponse;
import com.samara.order.service.model.OrderEntity;
import com.samara.order.service.model.OrderItem;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Component
public class Mapper {

    @Qualifier("productWebClient")
    private final WebClient productWebClient;

    public Mapper(WebClient productWebClient) {
        this.productWebClient = productWebClient;
    }

    public OrderResponse orderEntityToOrderResponse(OrderEntity order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .createdAt(LocalDateTime.now())
                .deletedAt(order.getDeletedAt())
                .modifiedAt(order.getModifiedAt())
                .totalPrice(order.getTotalPrice())
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

}