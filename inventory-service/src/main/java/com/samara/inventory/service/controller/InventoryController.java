package com.samara.inventory.service.controller;

import com.samara.inventory.service.bo.inventory.CreateInventoryRequest;
import com.samara.inventory.service.bo.inventory.InventoryResponse;
import com.samara.inventory.service.bo.inventory.UpdateInventoryRequest;
import com.samara.inventory.service.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventories")
    public ResponseEntity<List<InventoryResponse>> inventories() {
        return ResponseEntity.ok(inventoryService.inventories());
    }


    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> inventory(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.inventory(id));
    }

    @PostMapping("/add")
    public ResponseEntity<InventoryResponse> addInventory(@RequestBody CreateInventoryRequest createInventoryRequest) {
        return ResponseEntity.ok(inventoryService.addInventory(createInventoryRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<InventoryResponse> updateInventory(@RequestBody UpdateInventoryRequest updateInventoryRequest, @PathVariable Long id) {
        return new ResponseEntity<>(inventoryService.updateInventory(updateInventoryRequest, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable Long id) {
        return new ResponseEntity<>(inventoryService.deleteInventory(id).toString() + " ==> Deleted Successfully", HttpStatus.ACCEPTED);
    }
}