package com.samara.category.service.service;

import com.samara.category.service.bo.category.CategoryResponse;
import com.samara.category.service.bo.category.CreateCategoryRequest;
import com.samara.category.service.bo.category.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> categories();

    CategoryResponse categoryById(Long id);

    CategoryResponse saveCategory(CreateCategoryRequest categoryRequest);

    CategoryResponse updateCategory(Long id, UpdateCategoryRequest updateCategoryRequest);

    void deleteCategory(Long id);
}