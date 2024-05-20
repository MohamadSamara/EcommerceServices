package com.samara.cart.service.service;

import com.samara.cart.service.bo.cart.CartResponse;
import com.samara.cart.service.bo.cartItem.CreateCartItemRequest;
import com.samara.cart.service.mapper.Mapper;
import com.samara.cart.service.model.CartEntity;
import com.samara.cart.service.model.CartItem;
import com.samara.cart.service.repository.CartItemRepository;
import com.samara.cart.service.repository.CartRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final Mapper mapper;


    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, Mapper mapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.mapper = mapper;
    }


    @Override
    public CartResponse addToCart(CreateCartItemRequest createCartItemRequest, Long userId) {

        CartEntity userCart = createNewCartIfNotExist(userId, createCartItemRequest.getProductId());

        cartItemRepository.save(mapper.createCartItemRequestToCartItemEntity(createCartItemRequest, userCart));

        return mapper.EntityToCartResponse(userCart);
    }


    private CartEntity createNewCartIfNotExist(Long userId, Long productId) {
        Optional<CartEntity> userCart = cartRepository.findByUserId(userId);
        if (userCart.isEmpty()) {
            CartEntity newCart = CartEntity.builder()
                    .userId(userId)
                    .createdAt(LocalDateTime.now())
                    .totalPrice(mapper.getProduct(productId).getPrice())
                    .build();
            cartRepository.save(newCart);
            return newCart;
        }
        return userCart.get();
    }

    @Override
    public CartResponse cartDetails(Long userId) {

        CartEntity userCart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("No Cart Found"));

        List<CartItem> userCartItems = cartItemRepository.findAllByCartId(userCart);

        userCart.setCartItem(userCartItems);

        return mapper.EntityToCartResponse(userCart);

    }

    @Override
    public String deleteCart(Long userId) {
        CartEntity cartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("No Cart Found"));
        cartRepository.delete(cartEntity);
        return "Cart Deleted Successfully";
    }

    @Override
    public String deleteCartItem(Long userId, Long productId) {

        CartEntity cartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("No Cart Found"));

        CartItem cartItem = cartItemRepository.findByProductIdAndCartId(productId, cartEntity)
                .orElseThrow(() -> new NotFoundException("No Cart Found"));

        cartItemRepository.delete(cartItem);
        return "Cart Deleted Successfully";
    }

}