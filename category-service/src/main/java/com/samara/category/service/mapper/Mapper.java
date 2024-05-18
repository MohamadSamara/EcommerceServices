package com.samara.category.service.mapper;

import com.samara.category.service.bo.category.CategoryResponse;
import com.samara.category.service.bo.category.CreateCategoryRequest;
import com.samara.category.service.model.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public CategoryResponse EntityToCategoryResponse(CategoryEntity categoryEntity) {
        return CategoryResponse.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .createdAt(categoryEntity.getCreatedAt())
                .modifiedAt(categoryEntity.getModifiedAt())
                .deletedAt(categoryEntity.getDeletedAt())
                .build();
    }

    public CategoryEntity CategoryRequestToEntity(CreateCategoryRequest categoryRequest) {
        return CategoryEntity.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .createdAt(categoryRequest.getCreatedAt())
                .build();
    }

}