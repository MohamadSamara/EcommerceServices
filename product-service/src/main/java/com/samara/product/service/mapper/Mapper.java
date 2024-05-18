package com.samara.product.service.mapper;

import com.samara.product.service.bo.product.CreateProductRequest;
import com.samara.product.service.bo.product.ProductResponse;
import com.samara.product.service.model.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public ProductResponse EntityToProductResponse(ProductEntity productEntity) {
        return ProductResponse.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .inventoryId(null)
                .categoryId(null)
                .discountId(null)
                .price(productEntity.getPrice())
                .description(productEntity.getDescription())
                .createdAt(productEntity.getCreatedAt())
                .modifiedAt(productEntity.getModifiedAt())
                .deletedAt(productEntity.getDeletedAt())
                .build();
    }

    public ProductEntity ProductRequestToEntity(CreateProductRequest productRequest) {
        return ProductEntity.builder()
                .name(productRequest.getName())
                .inventoryId(productRequest.getInventoryId())
                .categoryId(productRequest.getCategoryId())
                .discountId(productRequest.getDiscountId())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .createdAt(productRequest.getCreatedAt())
                .build();

    }

}
