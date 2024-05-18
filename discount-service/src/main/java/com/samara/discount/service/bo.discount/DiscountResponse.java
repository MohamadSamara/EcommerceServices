package com.samara.discount.service.bo.discount;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DiscountResponse {
    private Long id;
    private String name;
    private String description;
    private Double discountPercent;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
}