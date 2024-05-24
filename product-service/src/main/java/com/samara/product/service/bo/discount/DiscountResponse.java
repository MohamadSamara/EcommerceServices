package com.samara.product.service.bo.discount;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class DiscountResponse implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Double discountPercent;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}