package com.samara.product.service.bo.product;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateProductRequest {
    private String name;
    private String description;
    private Long categoryId;
    private Long discountId;
    private Long inventoryId;
    private double price;
}