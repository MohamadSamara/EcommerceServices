package com.samara.inventory.service.service;

import com.samara.inventory.service.bo.inventory.CreateInventoryRequest;
import com.samara.inventory.service.bo.inventory.InventoryResponse;
import com.samara.inventory.service.bo.inventory.UpdateInventoryRequest;
import com.samara.inventory.service.mapper.Mapper;
import com.samara.inventory.service.model.InventoryEntity;
import com.samara.inventory.service.repository.InventoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final Mapper mapper;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, Mapper mapper) {
        this.inventoryRepository = inventoryRepository;
        this.mapper = mapper;
    }

    @Cacheable(value = "inventoryCache", unless = "#result.isEmpty()")
    @Override
    public List<InventoryResponse> inventories() {
        return inventoryRepository.findAll().stream().map(mapper::InventoryEntityToResponse).toList();
    }

    @Cacheable(value = "inventoryCache", key = "'inventory:'+#id")
    @Override
    public InventoryResponse inventory(Long id) {
        InventoryEntity inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found With ID => " + id));
        return mapper.InventoryEntityToResponse(inventory);
    }

    @CacheEvict(value = "inventoryCache", allEntries = true)
    @Override
    public InventoryResponse addInventory(CreateInventoryRequest createInventoryRequest) {

        createInventoryRequest.setCreatedAt(LocalDateTime.now());
        InventoryEntity inventoryEntity = inventoryRepository.save(mapper.createInventoryRequestToInventoryEntity(createInventoryRequest));
        return mapper.InventoryEntityToResponse(inventoryEntity);
    }

    @CacheEvict(value = "inventoryCache", key = "'inventory:'+#id", allEntries = true)
    @Override
    public InventoryResponse updateInventory(UpdateInventoryRequest updateInventoryRequest, Long id) {
        InventoryEntity inventoryEntity = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found With ID => " + id));
        updateInventoryHelper(inventoryEntity, updateInventoryRequest);
        inventoryRepository.save(inventoryEntity);
        return mapper.InventoryEntityToResponse(inventoryEntity);
    }

    private void updateInventoryHelper(InventoryEntity existingInventory, UpdateInventoryRequest updateInventoryRequest) {
        existingInventory.setQuantity(updateInventoryRequest.getQuantity());
        existingInventory.setName(updateInventoryRequest.getName());
        existingInventory.setModifiedAt(LocalDateTime.now());
    }

    @CacheEvict(value = "inventoryCache", allEntries = true)
    @Override
    public InventoryResponse deleteInventory(Long id) {
        InventoryEntity inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found With ID => " + id));
        inventoryRepository.delete(inventory);
        return mapper.InventoryEntityToResponse(inventory);
    }

}