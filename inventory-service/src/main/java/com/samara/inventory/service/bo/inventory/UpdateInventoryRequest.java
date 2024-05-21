package com.samara.inventory.service.bo.inventory;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UpdateInventoryRequest {
    private String name;
    private Long quantity;
    private LocalDateTime modifiedAt;
}