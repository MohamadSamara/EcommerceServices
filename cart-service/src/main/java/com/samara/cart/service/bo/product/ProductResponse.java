package com.samara.cart.service.bo.product;

import com.samara.cart.service.bo.category.CategoryResponse;
import com.samara.cart.service.bo.discount.DiscountResponse;
import com.samara.cart.service.bo.inventory.InventoryResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private double price;
    private CategoryResponse categoryId;
    private InventoryResponse inventoryId;
    private DiscountResponse discountId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
}