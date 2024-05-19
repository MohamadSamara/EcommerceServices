package com.samara.product.service.service;

import com.samara.product.service.bo.discount.DiscountResponse;
import com.samara.product.service.bo.product.CreateProductRequest;
import com.samara.product.service.bo.product.ProductResponse;
import com.samara.product.service.bo.product.UpdateProductRequest;
import com.samara.product.service.mapper.Mapper;
import com.samara.product.service.model.ProductEntity;
import com.samara.product.service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Mapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, Mapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }


    @Override
    public List<ProductResponse> products() {
        return productRepository.findAll().stream()
                .map(mapper::EntityToProductResponse)
                .toList();
    }


    @Override
    public ProductResponse productById(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found with id = " + id));
        productEntity.setPrice(addDiscountForProduct(productEntity, productEntity.getPrice()));
        return mapper.EntityToProductResponse(productEntity);
    }

    @Override
    public ProductResponse saveProduct(CreateProductRequest productRequest) {
        productRequest.setCreatedAt(LocalDateTime.now());
        productRequest.setPrice(addDiscountForProduct(mapper.ProductRequestToEntity(productRequest), productRequest.getPrice()));
        ProductEntity productEntity = mapper.ProductRequestToEntity(productRequest);
        mapper.increaseInventoryQuantity(productEntity.getInventoryId());
        productRepository.save(productEntity);
        return mapper.EntityToProductResponse(productEntity);
    }


    @Override
    public void deleteProduct(Long id) {
        ProductEntity productToDelete = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + " ID Not Found "));
        if (productToDelete != null) {
            productRepository.delete(productToDelete);
            mapper.decreaseInventoryQuantity(productToDelete.getInventoryId());
        }
    }


    @Override
    public ProductResponse updateProduct(Long id, UpdateProductRequest updateProductRequest) {
        ProductEntity existingProduct = productRepository.findById(id).
                orElseThrow(() -> new RuntimeException(id + " ID Not Found"));

        updateProductHelper(existingProduct, updateProductRequest);

        productRepository.save(existingProduct);
        return mapper.EntityToProductResponse(existingProduct);
    }

    private void updateProductHelper(ProductEntity existingProduct, UpdateProductRequest updateProductRequest) {
        /*
         * For the Price :
         * If the user change only the discount percent we can handle it
         * If the user change only the price we can handle it
         * If the user change the both we can't handle it, so we have to calculate it always
         */
        existingProduct.setName(updateProductRequest.getName());
        existingProduct.setDescription(updateProductRequest.getDescription());
        existingProduct.setDiscountId(updateProductRequest.getDiscountId());
        double price = addDiscountForProduct(existingProduct, updateProductRequest.getPrice());
        existingProduct.setPrice(price);
        existingProduct.setCategoryId(updateProductRequest.getCategoryId());
        existingProduct.setInventoryId(updateProductRequest.getInventoryId());
        existingProduct.setModifiedAt(LocalDateTime.now());
    }


    private double addDiscountForProduct(ProductEntity productEntity, double price) {
        DiscountResponse discountResponse = mapper.getDiscountFromProduct(productEntity.getDiscountId());
        if (discountResponse != null && discountResponse.getIsActive()) {
            return calculatePriceAfterDiscount(price, discountResponse.getDiscountPercent());
        } else {
            throw new RuntimeException("Discount Is Not Active Or Not Found With ID = " + productEntity.getDiscountId());
        }
    }

    private double calculatePriceAfterDiscount(double price, double discountPercent) {
        return price * (discountPercent / 100);
    }
  /*
  * How To Deal With WebClint :

  public List<CategoryResponse> getCategoryFromProduct() {

        ResponseEntity<List<CategoryResponse>> responseEntity =
                categoryWebClient.get()
                        .uri(
                                uriBuilder -> uriBuilder.path("/api/v1/category/categories").build()
                        )
                        .retrieve()
                        .toEntityList(CategoryResponse.class)
                        .block(); // block() method is used to block until the result is available

        if (responseEntity != null && responseEntity.hasBody()) {
            return responseEntity.getBody();
        } else {
            return Collections.emptyList();
        }
    }*/


}