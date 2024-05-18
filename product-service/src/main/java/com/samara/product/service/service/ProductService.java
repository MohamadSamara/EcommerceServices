package com.samara.product.service.service;

import com.samara.product.service.bo.product.CreateProductRequest;
import com.samara.product.service.bo.product.ProductResponse;
import com.samara.product.service.bo.product.UpdateProductRequest;

import java.util.List;

public interface ProductService {
    List<ProductResponse> products();

    ProductResponse productById(Long id);

    ProductResponse saveProduct(CreateProductRequest productRequest);

    ProductResponse updateProduct(Long id, UpdateProductRequest updateProductRequest);

    void deleteProduct(Long id);
}
