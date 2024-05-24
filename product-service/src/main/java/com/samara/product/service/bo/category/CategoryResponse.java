package com.samara.product.service.bo.category;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryResponse implements Serializable {
    private Long id;
    private String name;
    private String description;
}