package com.samara.product.service.bo.product;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class UpdateProductRequest {
    private String name;
    private String description;
    private Long categoryId;
    private Long inventoryId;
    private Double price;
    private Long discountId;
    private LocalDateTime modifiedAt;
}