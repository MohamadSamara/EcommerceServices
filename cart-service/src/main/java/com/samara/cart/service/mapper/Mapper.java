package com.samara.cart.service.mapper;

import com.samara.cart.service.bo.cart.CartResponse;
import com.samara.cart.service.bo.cart.CreateCartRequest;
import com.samara.cart.service.bo.product.ProductResponse;
import com.samara.cart.service.model.CartEntity;
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

    public CartResponse EntityToCartResponse(CartEntity cartEntity) {


        return CartResponse.builder()
                .id(cartEntity.getId())
                .userId(cartEntity.getUserId())
                .productId(getProduct(cartEntity.getProductId()))
                .quantity(cartEntity.getQuantity())
                .createdAt(cartEntity.getCreatedAt())
                .modifiedAt(cartEntity.getModifiedAt())
                .build();
    }

    public CartEntity CartRequestToEntity(CreateCartRequest cartRequest) {
        return CartEntity.builder()
                .userId(cartRequest.getUserId())
                .productId(cartRequest.getProductId())
                .quantity(cartRequest.getQuantity())
                .createdAt(LocalDateTime.now())
                .build();
    }


    private ProductResponse getProduct(Long id) {

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

}