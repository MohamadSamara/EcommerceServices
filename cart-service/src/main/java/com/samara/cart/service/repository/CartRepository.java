package com.samara.cart.service.repository;

import com.samara.cart.service.model.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    List<CartEntity> findAllByUserId(Long userId);

    Optional<CartEntity> findByUserIdAndProductId(Long userId, Long productId);
}