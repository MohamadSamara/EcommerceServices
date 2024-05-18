package com.samara.category.service.service;

import com.samara.category.service.bo.category.CategoryResponse;
import com.samara.category.service.bo.category.CreateCategoryRequest;
import com.samara.category.service.bo.category.UpdateCategoryRequest;
import com.samara.category.service.mapper.Mapper;
import com.samara.category.service.model.CategoryEntity;
import com.samara.category.service.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, Mapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }


    @Override
    public List<CategoryResponse> categories() {
        return categoryRepository.findAll().stream().map(mapper::EntityToCategoryResponse).toList();
    }

    @Override
    public CategoryResponse categoryById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found with id = " + id));
        return mapper.EntityToCategoryResponse(categoryEntity);
    }

    @Override
    public CategoryResponse saveCategory(CreateCategoryRequest categoryRequest) {
        categoryRequest.setCreatedAt(LocalDateTime.now());
        CategoryEntity categoryEntity = categoryRepository.save(mapper.CategoryRequestToEntity(categoryRequest));
        return mapper.EntityToCategoryResponse(categoryEntity);
    }

    @Override
    public void deleteCategory(Long id) {
        CategoryEntity categoryToDelete = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + " ID Not Found "));
        if (categoryToDelete != null) {
            categoryRepository.delete(categoryToDelete);
        }
    }

    @Override
    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest updateCategoryRequest) {
        CategoryEntity existingCategory = categoryRepository.findById(id).
                orElseThrow(() -> new RuntimeException(id + " ID Not Found"));

        updateCategoryHelper(existingCategory, updateCategoryRequest);

        categoryRepository.save(existingCategory);
        return mapper.EntityToCategoryResponse(existingCategory);
    }

    private void updateCategoryHelper(CategoryEntity existingCategory, UpdateCategoryRequest updateCategoryRequest) {
        existingCategory.setName(updateCategoryRequest.getName());
        existingCategory.setDescription(updateCategoryRequest.getDescription());
        existingCategory.setModifiedAt(LocalDateTime.now());
    }

}