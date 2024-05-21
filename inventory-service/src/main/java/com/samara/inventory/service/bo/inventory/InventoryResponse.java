package com.samara.inventory.service.bo.inventory;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryResponse {
    private Long id;
    private String name;
    private Long quantity;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}