package com.samara.product.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mst_product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "inventory_id", nullable = false)
    private Long inventoryId;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "sale_price", nullable = false)
    private Double salePrice;

    @Column(name = "discount_id", nullable = false)
    private Long discountId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}

