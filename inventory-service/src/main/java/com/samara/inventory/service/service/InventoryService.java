package com.samara.inventory.service.service;

import com.samara.inventory.service.bo.inventory.CreateInventoryRequest;
import com.samara.inventory.service.bo.inventory.InventoryResponse;
import com.samara.inventory.service.bo.inventory.UpdateInventoryRequest;

import java.util.List;

public interface InventoryService {

    List<InventoryResponse> inventories();

    InventoryResponse inventory(Long id);

    InventoryResponse addInventory(CreateInventoryRequest createInventoryRequest);

    InventoryResponse updateInventory(UpdateInventoryRequest updateInventoryRequest, Long id);

    InventoryResponse deleteInventory(Long id);
}