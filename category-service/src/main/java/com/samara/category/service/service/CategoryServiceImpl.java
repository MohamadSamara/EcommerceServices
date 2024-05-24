package com.samara.category.service.service;

import com.samara.category.service.bo.category.CategoryResponse;
import com.samara.category.service.bo.category.CreateCategoryRequest;
import com.samara.category.service.bo.category.UpdateCategoryRequest;
import com.samara.category.service.mapper.Mapper;
import com.samara.category.service.model.CategoryEntity;
import com.samara.category.service.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

    // private final RedisTemplate<String, CategoryEntity> categoryRedisTemplate;
    public CategoryServiceImpl(CategoryRepository categoryRepository, Mapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }


    @Cacheable(value = "categoryCache", unless = "#result.isEmpty()")
    @Override
    public List<CategoryResponse> categories() {
        return categoryRepository.findAll().stream().map(mapper::EntityToCategoryResponse).toList();
    }

    // If id already in the cash the compiler will not enter the function
    @Cacheable(value = "categoryCache", key = "'category:'+#id")
    @Override
    public CategoryResponse categoryById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found with id = " + id));
        log.info("From DB");
        return mapper.EntityToCategoryResponse(categoryEntity);
    }

    @CacheEvict(value = "categoryCache", allEntries = true)
    @Override
    public CategoryResponse saveCategory(CreateCategoryRequest categoryRequest) {
        categoryRequest.setCreatedAt(LocalDateTime.now());
        CategoryEntity categoryEntity = categoryRepository.save(mapper.CategoryRequestToEntity(categoryRequest));
        log.info("Category Saved To DB And Cache {}", categoryEntity);
        return mapper.EntityToCategoryResponse(categoryEntity);
    }

    @CacheEvict(value = "categoryCache", allEntries = true)
    @Override
    public void deleteCategory(Long id) {
        CategoryEntity categoryToDelete = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + " ID Not Found "));
        if (categoryToDelete != null) {
            categoryRepository.delete(categoryToDelete);
            log.info("Category Removed From Cache And DB");
        }
    }

    @CacheEvict(value = "categoryCache", key = "'category:'+#id", allEntries = true)
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
    /*
    * If We Want to update using opsForValue instead of @CacheEvict(value = "categoryCache", key = "#id") :
    * But this way not Effective

    @Override
    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest updateCategoryRequest) {
    String key = id.toString();
    CategoryEntity cachedCategory = categoryRedisTemplate.opsForValue().get(key);

    if (cachedCategory != null) {
        // Update cached data
        cachedCategory.setName(updateCategoryRequest.getName());
        cachedCategory.setDescription(updateCategoryRequest.getDescription());
        cachedCategory.setModifiedAt(LocalDateTime.now());

        // Update database with modified cached data
        categoryRepository.save(cachedCategory);

        // Update cache with modified data
        categoryRedisTemplate.opsForValue().set(key, cachedCategory);
        log.info("Category Updated in Cache and DB for {}", id);
        return mapper.EntityToCategoryResponse(cachedCategory);
    } else {
        // Cache miss, fetch from database and update
        CategoryEntity existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + " ID Not Found"));

        updateCategoryHelper(existingCategory, updateCategoryRequest);

        categoryRepository.save(existingCategory);
        categoryRedisTemplate.opsForValue().set(key, existingCategory);
        log.info("Category Updated in DB and Cache (Miss) for {}", id);
        return mapper.EntityToCategoryResponse(existingCategory);
    }
}
    */
