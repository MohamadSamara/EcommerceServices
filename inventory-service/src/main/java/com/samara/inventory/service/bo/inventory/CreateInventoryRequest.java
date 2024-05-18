package com.samara.inventory.service.bo.inventory;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateInventoryRequest {
    private Long quantity;
    private LocalDateTime CreatedAt;
}