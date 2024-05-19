package com.samara.cart.service.service;

import com.samara.cart.service.bo.cart.CartResponse;
import com.samara.cart.service.bo.cart.CreateCartRequest;
import com.samara.cart.service.bo.cart.UpdateCartRequest;
import com.samara.cart.service.mapper.Mapper;
import com.samara.cart.service.model.CartEntity;
import com.samara.cart.service.repository.CartRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final Mapper mapper;

    public CartServiceImpl(CartRepository cartRepository, Mapper mapper) {
        this.cartRepository = cartRepository;
        this.mapper = mapper;
    }


    @Override
    public CartResponse addToCart(CreateCartRequest createCartRequest, Long userId) {
        createCartRequest.setUserId(userId);
        CartEntity cartEntity = mapper.CartRequestToEntity(createCartRequest);
        cartRepository.save(cartEntity);
        return mapper.EntityToCartResponse(cartEntity);
    }


    @Override
    public List<CartResponse> cartDetails(Long userId) {
        return cartRepository.findAllByUserId(userId)
                .stream()
                .map(mapper::EntityToCartResponse)
                .toList();
    }

    @Override
    public String deleteCart(Long userId, Long productId) {

        CartEntity cartEntity = cartRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new NotFoundException("No Cart Found"));

        cartRepository.delete(cartEntity);

        return "Cart Deleted Successfully";
    }

    @Override
    public CartResponse updateCartQuantity(Long userId, Long productId, UpdateCartRequest updateCartRequest) {
        CartEntity cartEntity = cartRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new NotFoundException("No Cart Found"));

        cartEntity.setQuantity(updateCartRequest.getQuantity());
        cartEntity.setModifiedAt(LocalDateTime.now());

        cartRepository.save(cartEntity);

        return mapper.EntityToCartResponse(cartEntity);
    }

}