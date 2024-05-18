package com.samara.inventory.service.repository;

import com.samara.inventory.service.model.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
}