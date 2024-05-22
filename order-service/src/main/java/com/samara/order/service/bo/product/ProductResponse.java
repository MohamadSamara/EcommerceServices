package com.samara.order.service.bo.product;

import com.samara.order.service.bo.category.CategoryResponse;
import com.samara.order.service.bo.discount.DiscountResponse;
import com.samara.order.service.bo.inventory.InventoryResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double salePrice;
    private CategoryResponse categoryId;
    private InventoryResponse inventoryId;
    private DiscountResponse discountId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
}