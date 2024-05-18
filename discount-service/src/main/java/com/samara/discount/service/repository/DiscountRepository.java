package com.samara.discount.service.repository;

import com.samara.discount.service.model.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {
}