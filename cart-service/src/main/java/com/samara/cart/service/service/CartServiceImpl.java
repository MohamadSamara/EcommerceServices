package com.samara.cart.service.service;


import com.samara.cart.service.bo.cart.CartResponse;
import com.samara.cart.service.bo.cart.CreateCartRequest;
import com.samara.cart.service.bo.user.UserResponse;
import com.samara.cart.service.mapper.Mapper;
import com.samara.cart.service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final Mapper mapper;

    @Qualifier("userWebClient")
    private final WebClient userWebClient;

    public CartServiceImpl(CartRepository cartRepository, Mapper mapper, WebClient userWebClient) {
        this.cartRepository = cartRepository;
        this.mapper = mapper;
        this.userWebClient = userWebClient;
    }


    @Override
    public CartResponse addToCart(CreateCartRequest createCartRequest) {
//
//        ProductResponse productResponse = getProduct(createCartRequest);
//        UserResponse userResponse = getUser(createCartRequest);
//
//        createCartRequest.setProductId(productResponse.getId());
//        createCartRequest.setUserId(userResponse.getId());
//
//        CartEntity cartEntity = mapper.CartRequestToEntity(createCartRequest);
//        cartRepository.save(cartEntity);
//        CartResponse cartResponse = mapper.EntityToCartResponse(cartEntity);
        return null;
    }

    private UserResponse getUser(CreateCartRequest request) {

        ResponseEntity<UserResponse> userResponse =
                userWebClient.get()
                        .uri(
                                uri -> uri
                                        .path("/{username}")
                                        .build(request.getUserId()) // how to get the username ?????
                        )
                        .retrieve()
                        .toEntity(UserResponse.class)
                        .block();

        if (userResponse != null) {
            return userResponse.getBody();
        } else {
            throw new RuntimeException("Something Wrong in UserResponse ...");
        }
    }


    @Override
    public List<CartResponse> cartDetails(Long userId) {
        return cartRepository.findAllByUserId(userId)
                .stream()
                .map(mapper::EntityToCartResponse)
                .toList();
    }

}