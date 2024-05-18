package com.samara.inventory.service.mapper;

import com.samara.inventory.service.bo.inventory.CreateInventoryRequest;
import com.samara.inventory.service.bo.inventory.InventoryResponse;
import com.samara.inventory.service.model.InventoryEntity;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

//    public InventoryEntity responseInventoryToInventoryEntity(InventoryResponse inventoryResponse) {
//        return InventoryEntity.builder()
//                .id(inventoryResponse.getId())
//                .quantity(inventoryResponse.getQuantity())
//                .createdAt(inventoryResponse.getCreatedAt())
//                .modifiedAt(inventoryResponse.getModifiedAt())
//                .build();
//    }

    public InventoryResponse InventoryEntityToResponse(InventoryEntity inventoryEntity) {
        return InventoryResponse.builder()
                .id(inventoryEntity.getId())
                .quantity(inventoryEntity.getQuantity())
                .createdAt(inventoryEntity.getCreatedAt())
                .modifiedAt(inventoryEntity.getModifiedAt())
                .build();
    }


    public InventoryEntity createInventoryRequestToInventoryEntity(CreateInventoryRequest createInventoryRequest) {
        return InventoryEntity.builder()
                .quantity(createInventoryRequest.getQuantity())
                .createdAt(createInventoryRequest.getCreatedAt())
                .build();
    }

}