package com.samara.product.service.controller;

import com.samara.product.service.bo.product.CreateProductRequest;
import com.samara.product.service.bo.product.ProductResponse;
import com.samara.product.service.bo.product.UpdateProductRequest;
import com.samara.product.service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> products() {
        return new ResponseEntity<>(productService.products(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> productById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.productById(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<ProductResponse> saveProduct(@RequestBody CreateProductRequest productRequest) {
        return new ResponseEntity<>(productService.saveProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest updateProductRequest) {
        return new ResponseEntity<>(productService.updateProduct(id, updateProductRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(id + " Id Product Deleted Successfully", HttpStatus.ACCEPTED);
    }

}
