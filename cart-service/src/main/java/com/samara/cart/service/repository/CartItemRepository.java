package com.samara.cart.service.repository;


import com.samara.cart.service.model.CartEntity;
import com.samara.cart.service.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByCartId(CartEntity cartId);

    Optional<CartItem> findByProductIdAndCartId(Long productId, CartEntity cartId);
}