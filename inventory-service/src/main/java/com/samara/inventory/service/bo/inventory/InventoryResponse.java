package com.samara.inventory.service.bo.inventory;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class InventoryResponse implements Serializable {
    private Long id;
    private String name;
    private Long quantity;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}