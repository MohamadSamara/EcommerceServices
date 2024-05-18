package com.samara.category.service.bo.category;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateCategoryRequest {
    private String name;
    private String description;
    private LocalDateTime createdAt;
}