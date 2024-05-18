package com.samara.discount.service.bo.discount;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateDiscountRequest {
    private String name;
    private String description;
    private Double discountPercent;
    private Boolean isActive;
    private LocalDateTime createdAt;
}