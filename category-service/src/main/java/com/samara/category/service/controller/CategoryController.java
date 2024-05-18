package com.samara.category.service.controller;

import com.samara.category.service.bo.category.CategoryResponse;
import com.samara.category.service.bo.category.CreateCategoryRequest;
import com.samara.category.service.bo.category.UpdateCategoryRequest;
import com.samara.category.service.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> categories() {
        return new ResponseEntity<>(categoryService.categories(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> categoryById(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.categoryById(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<CategoryResponse> saveCategory(@RequestBody CreateCategoryRequest categoryRequest) {
        return new ResponseEntity<>(categoryService.saveCategory(categoryRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        return new ResponseEntity<>(categoryService.updateCategory(id, updateCategoryRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(id + " Id Category Deleted Successfully", HttpStatus.ACCEPTED);
    }

}