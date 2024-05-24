package com.samara.product.service.bo.product;

import com.samara.product.service.bo.category.CategoryResponse;
import com.samara.product.service.bo.discount.DiscountResponse;
import com.samara.product.service.bo.inventory.InventoryResponse;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponse implements Serializable {
    private Long id;
    private String name;
    private String description;
    private double price;
    private double salePrice;
    private CategoryResponse categoryId;
    private InventoryResponse inventoryId;
    private DiscountResponse discountId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
}