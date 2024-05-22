package com.samara.order.service.bo.inventory;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryResponse {
    private Long id;
    private Long quantity;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}