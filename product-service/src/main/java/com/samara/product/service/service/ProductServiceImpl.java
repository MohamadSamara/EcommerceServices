package com.samara.product.service.service;

import com.samara.product.service.bo.category.CategoryResponse;
import com.samara.product.service.bo.discount.DiscountResponse;
import com.samara.product.service.bo.inventory.InventoryResponse;
import com.samara.product.service.bo.product.CreateProductRequest;
import com.samara.product.service.bo.product.ProductResponse;
import com.samara.product.service.bo.product.UpdateProductRequest;
import com.samara.product.service.mapper.Mapper;
import com.samara.product.service.model.ProductEntity;
import com.samara.product.service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Mapper mapper;
    @Qualifier("categoryWebClient")
    private final WebClient categoryWebClient;
    @Qualifier("inventoryWebClient")
    private final WebClient inventoryWebClient;
    @Qualifier("discountWebClient")
    private final WebClient discountWebClient;

    public ProductServiceImpl(ProductRepository productRepository, Mapper mapper, WebClient categoryWebClient, WebClient inventoryWebClient, WebClient discountWebClient) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.categoryWebClient = categoryWebClient;
        this.inventoryWebClient = inventoryWebClient;
        this.discountWebClient = discountWebClient;
    }


    @Override
    public List<ProductResponse> products() {
        return productRepository.findAll().stream()
                .map(productEntity -> {
                    ProductResponse productResponse = mapper.EntityToProductResponse(productEntity);
                    CategoryResponse category = getCategoryFromProduct(productEntity.getCategoryId()); // Get the Category Object from Category-service
                    InventoryResponse inventory = getInventoryFromProduct(productEntity.getInventoryId()); // Get the Inventory Object from Inventory-service
                    DiscountResponse discount = getDiscountFromProduct(productEntity.getDiscountId()); // Get the Inventory Object from Inventory-service
                    productResponse.setCategoryId(category);
                    productResponse.setInventoryId(inventory);
                    productResponse.setDiscountId(discount);
                    return productResponse;
                })
                .toList();
    }

    // Helper Function To Get The Inventory Object from Inventory-Service
    private InventoryResponse getInventoryFromProduct(Long inventoryId) {
        ResponseEntity<InventoryResponse> inventoryResponseEntity =
                inventoryWebClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/api/v1/inventory/{id}")
                                .build(inventoryId)
                        )
                        .retrieve()
                        .toEntity(InventoryResponse.class)
                        .block();

        if (inventoryResponseEntity != null && inventoryResponseEntity.hasBody()) {
            return inventoryResponseEntity.getBody();
        } else {
            return new InventoryResponse();
        }
    }

    // Helper Function To Get The Category Object from Category-Service
    private CategoryResponse getCategoryFromProduct(Long productEntityId) {
        ResponseEntity<CategoryResponse> categoryResponseEntity =
                categoryWebClient.get()
                        .uri(
                                uriBuilder -> uriBuilder
                                        .path("/api/v1/category/{id}")
                                        .build(productEntityId)
                        )
                        .retrieve()
                        .toEntity(CategoryResponse.class)
                        .block();
        if (categoryResponseEntity != null && categoryResponseEntity.hasBody()) {
            return categoryResponseEntity.getBody();
        } else {
            return new CategoryResponse();
        }
    }

    // Helper Function To Get The Discount Object from Discount-Service
    private DiscountResponse getDiscountFromProduct(Long discountId) {
        ResponseEntity<DiscountResponse> discountResponseEntity =
                discountWebClient.get()
                        .uri(
                                uriBuilder -> uriBuilder
                                        .path("/api/v1/discount/{id}")
                                        .build(discountId)
                        )
                        .retrieve()
                        .toEntity(DiscountResponse.class)
                        .block();
        if (discountResponseEntity != null && discountResponseEntity.hasBody()) {
            return discountResponseEntity.getBody();
        } else {
            return new DiscountResponse();
        }
    }

    @Override
    public ProductResponse productById(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found with id = " + id));
        ProductResponse productResponse = mapper.EntityToProductResponse(productEntity);
        productResponse.setInventoryId(getInventoryFromProduct(productEntity.getInventoryId()));
        productResponse.setCategoryId(getCategoryFromProduct(productEntity.getCategoryId()));
        return productResponse;
    }

    @Override
    public ProductResponse saveProduct(CreateProductRequest productRequest) {
        productRequest.setCreatedAt(LocalDateTime.now());
        productRequest.setPrice(addDiscountForProduct(mapper.ProductRequestToEntity(productRequest), productRequest.getPrice()));
        ProductEntity productEntity = mapper.ProductRequestToEntity(productRequest);
        increaseInventoryQuantity(productEntity.getInventoryId());
        productRepository.save(productEntity);
        return mapper.EntityToProductResponse(productEntity);
    }

    private void increaseInventoryQuantity(Long inventoryId) {
        InventoryResponse inventory = getInventoryFromProduct(inventoryId);
        inventory.setQuantity(inventory.getQuantity() + 1);
        inventoryWebClient.put()
                .uri(uriBuilder ->
                        uriBuilder.path("api/v1/inventory/update/{id}").build(inventoryId)
                )
                .bodyValue(inventory)
                .retrieve()
                .toBodilessEntity()
                .block();
    }


    @Override
    public void deleteProduct(Long id) {
        ProductEntity productToDelete = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + " ID Not Found "));
        if (productToDelete != null) {
            productRepository.delete(productToDelete);
            decreaseInventoryQuantity(productToDelete.getInventoryId());
        }
    }

    private void decreaseInventoryQuantity(Long inventoryId) {
        InventoryResponse inventory = getInventoryFromProduct(inventoryId);
        inventory.setQuantity(inventory.getQuantity() - 1);
        inventoryWebClient.put()
                .uri(uriBuilder ->
                        uriBuilder.path("api/v1/inventory/update/{id}").build(inventoryId)
                )
                .bodyValue(inventory)
                .retrieve()
                .toBodilessEntity()
                .block();
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
        DiscountResponse discountResponse = getDiscountFromProduct(productEntity.getDiscountId());
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