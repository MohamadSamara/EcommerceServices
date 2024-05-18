package com.samara.product.service.bo.product;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateProductRequest {
    private String name;
    private String description;
    private Long categoryId;
    private Long discountId;
    private Long inventoryId;
    private double price;
    private LocalDateTime createdAt;
}
