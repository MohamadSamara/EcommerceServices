package com.samara.cart.service.bo.product;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
//@RequiredArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Long categoryId;
    private Long inventoryId;
    private Long discountId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
}