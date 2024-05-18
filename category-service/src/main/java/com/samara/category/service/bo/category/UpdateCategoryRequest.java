package com.samara.category.service.bo.category;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateCategoryRequest {
    private String name;
    private String description;
}