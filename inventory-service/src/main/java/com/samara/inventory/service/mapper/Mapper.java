package com.samara.inventory.service.mapper;

import com.samara.inventory.service.bo.inventory.CreateInventoryRequest;
import com.samara.inventory.service.bo.inventory.InventoryResponse;
import com.samara.inventory.service.model.InventoryEntity;
import org.springframework.stereotype.Component;

@Component
public class Mapper {


    public InventoryResponse InventoryEntityToResponse(InventoryEntity inventoryEntity) {
        return InventoryResponse.builder()
                .id(inventoryEntity.getId())
                .name(inventoryEntity.getName())
                .quantity(inventoryEntity.getQuantity())
                .createdAt(inventoryEntity.getCreatedAt())
                .modifiedAt(inventoryEntity.getModifiedAt())
                .build();
    }


    public InventoryEntity createInventoryRequestToInventoryEntity(CreateInventoryRequest createInventoryRequest) {
        return InventoryEntity.builder()
                .name(createInventoryRequest.getName())
                .quantity(createInventoryRequest.getQuantity())
                .createdAt(createInventoryRequest.getCreatedAt())
                .build();
    }

}