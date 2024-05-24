package com.samara.product.service.bo.inventory;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class InventoryResponse implements Serializable {
    private Long id;
    private String name;
    private Long quantity;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}