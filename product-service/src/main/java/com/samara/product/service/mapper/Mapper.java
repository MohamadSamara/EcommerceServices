package com.samara.product.service.mapper;

import com.samara.product.service.bo.category.CategoryResponse;
import com.samara.product.service.bo.discount.DiscountResponse;
import com.samara.product.service.bo.inventory.InventoryResponse;
import com.samara.product.service.bo.product.CreateProductRequest;
import com.samara.product.service.bo.product.ProductResponse;
import com.samara.product.service.model.ProductEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Mapper {

    @Qualifier("categoryWebClient")
    private final WebClient categoryWebClient;
    @Qualifier("inventoryWebClient")
    private final WebClient inventoryWebClient;
    @Qualifier("discountWebClient")
    private final WebClient discountWebClient;

    public Mapper(WebClient categoryWebClient, WebClient inventoryWebClient, WebClient discountWebClient) {
        this.categoryWebClient = categoryWebClient;
        this.inventoryWebClient = inventoryWebClient;
        this.discountWebClient = discountWebClient;
    }

    public ProductResponse EntityToProductResponse(ProductEntity productEntity) {
        return ProductResponse.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .inventoryId(getInventoryFromProduct(productEntity.getInventoryId()))
                .categoryId(getCategoryFromProduct(productEntity.getCategoryId()))
                .discountId(getDiscountFromProduct(productEntity.getDiscountId()))
                .price(productEntity.getPrice())
                .salePrice(productEntity.getSalePrice())
                .description(productEntity.getDescription())
                .createdAt(productEntity.getCreatedAt())
                .modifiedAt(productEntity.getModifiedAt())
                .deletedAt(productEntity.getDeletedAt())
                .build();
    }

    public ProductEntity ProductRequestToEntity(CreateProductRequest productRequest) {
        return ProductEntity.builder()
                .name(productRequest.getName())
                .inventoryId(productRequest.getInventoryId())
                .categoryId(productRequest.getCategoryId())
                .discountId(productRequest.getDiscountId())
                .price(productRequest.getPrice())
                .salePrice(productRequest.getSalePrice())
                .description(productRequest.getDescription())
                .createdAt(productRequest.getCreatedAt())
                .build();
    }


    public void increaseInventoryQuantity(Long inventoryId) {
        InventoryResponse inventory = getInventoryFromProduct(inventoryId);
        inventory.setQuantity(inventory.getQuantity() + 1);
        inventoryWebClient.put()
                .uri(uriBuilder ->
                        uriBuilder.path("/update/{id}").build(inventoryId)
                )
                .bodyValue(inventory)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void decreaseInventoryQuantity(Long inventoryId) {
        InventoryResponse inventory = getInventoryFromProduct(inventoryId);
        inventory.setQuantity(inventory.getQuantity() - 1);
        inventoryWebClient.put()
                .uri(uriBuilder ->
                        uriBuilder.path("/update/{id}").build(inventoryId)
                )
                .bodyValue(inventory)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public DiscountResponse getDiscountFromProduct(Long discountId) {
        ResponseEntity<DiscountResponse> discountResponseEntity =
                discountWebClient.get()
                        .uri(
                                uriBuilder -> uriBuilder
                                        .path("/{id}")
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

    public InventoryResponse getInventoryFromProduct(Long inventoryId) {
        ResponseEntity<InventoryResponse> inventoryResponseEntity =
                inventoryWebClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/{id}")
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
    }

*/

}
