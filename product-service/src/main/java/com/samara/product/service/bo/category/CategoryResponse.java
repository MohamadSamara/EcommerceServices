package com.samara.product.service.bo.category;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
}