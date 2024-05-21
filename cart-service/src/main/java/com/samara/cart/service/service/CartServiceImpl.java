package com.samara.cart.service.service;

import com.samara.cart.service.bo.cart.CartResponse;
import com.samara.cart.service.bo.cart.UpdateCartRequest;
import com.samara.cart.service.bo.cartItem.CreateCartItemRequest;
import com.samara.cart.service.bo.product.ProductResponse;
import com.samara.cart.service.mapper.Mapper;
import com.samara.cart.service.model.CartEntity;
import com.samara.cart.service.model.CartItem;
import com.samara.cart.service.repository.CartItemRepository;
import com.samara.cart.service.repository.CartRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        CartEntity userCart = createNewCartIfNotExist(userId);
        CartItem cartItem = mapper.createCartItemRequestToCartItemEntity(createCartItemRequest, userCart);

        userCart.getCartItem().add(cartItem);
        userCart.setTotalPrice(userCart.getTotalPrice() + cartItem.getTotalPrice());
        userCart.setModifiedAt(LocalDateTime.now());

        cartRepository.save(userCart);
        return mapper.EntityToCartResponse(userCart);
    }


    private CartEntity createNewCartIfNotExist(Long userId) {
        Optional<CartEntity> userCart = cartRepository.findByUserId(userId);
        return userCart.orElseGet(
                () -> CartEntity.builder()
                        .userId(userId)
                        .createdAt(LocalDateTime.now())
                        .totalPrice(0.0)
                        .cartItem(new ArrayList<>()) // Initialize the cart item list here
                        .build()
        );
    }

    @Override
    public CartResponse cartDetails(Long userId) {
        CartEntity userCart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No Cart Found (Add Item To See the Details)"));
        List<CartItem> userCartItems = cartItemRepository.findAllByCartId(userCart);
        userCart.setCartItem(userCartItems);
        return mapper.EntityToCartResponse(userCart);
    }


    @Override
    public String deleteCartItem(Long userId, Long productId) {

        CartEntity cartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("No Cart Found"));

        CartItem cartItem = cartItemRepository.findByProductIdAndCartId(productId, cartEntity)
                .orElseThrow(() -> new NotFoundException("No Cart Found"));

        cartItemRepository.delete(cartItem);
        updateTotalPriceForCartWhenDelete(cartEntity, cartItem.getTotalPrice());
        return "Cart Deleted Successfully";
    }


    private void updateTotalPriceForCartWhenDelete(CartEntity cartEntity, Double cartItemPrice) {
        cartEntity.setModifiedAt(LocalDateTime.now());
        cartEntity.setTotalPrice(cartEntity.getTotalPrice() - cartItemPrice);
        cartRepository.save(cartEntity);
    }

    @Override
    public String updateCartQuantity(UpdateCartRequest updateCartRequest, Long userId, Long productId) {
        if (updateCartRequest.getQuantity() == 0) {
            return deleteCartItem(userId, productId);
        }
        CartEntity cartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("No Cart Found"));

        CartItem cartItem = cartItemRepository.findByProductIdAndCartId(productId, cartEntity)
                .orElseThrow(() -> new NotFoundException("No Cart Found"));

        cartItem.setQuantity(updateCartRequest.getQuantity());
        updateTotalPriceForCartItem(cartItem);
        updateTotalPriceForCartWhenUpdate(cartEntity);
        return "Quantity Updated Successfully";
    }

    private void updateTotalPriceForCartWhenUpdate(CartEntity cartEntity) {
        Double totalPrice = cartItemRepository.findByCartId(cartEntity)
                .stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
        cartEntity.setModifiedAt(LocalDateTime.now());
        cartEntity.setTotalPrice(totalPrice);
        cartRepository.save(cartEntity);
    }

    private void updateTotalPriceForCartItem(CartItem cartItem) {
        ProductResponse product = mapper.getProduct(cartItem.getProductId());
        cartItem.setTotalPrice(product.getPrice() * cartItem.getQuantity());
        cartItemRepository.save(cartItem);
    }


}