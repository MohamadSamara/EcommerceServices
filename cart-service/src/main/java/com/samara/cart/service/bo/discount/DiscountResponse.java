package com.samara.cart.service.bo.discount;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiscountResponse {
    private Long id;
    private String name;
    private String description;
    private Double discountPercent;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}