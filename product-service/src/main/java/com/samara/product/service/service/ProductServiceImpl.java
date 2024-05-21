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
        return mapper.EntityToProductResponse(productEntity);
    }

    @Override
    public ProductResponse saveProduct(CreateProductRequest productRequest) {
        productRequest.setCreatedAt(LocalDateTime.now());
        productRequest.setSalePrice(calculatePriceAfterDiscountForProduct(productRequest.getDiscountId(), productRequest.getPrice()));
        ProductEntity productEntity = mapper.ProductRequestToEntity(productRequest);
        productRepository.save(productEntity);
        mapper.increaseInventoryQuantity(productEntity.getInventoryId());
        return mapper.EntityToProductResponse(productEntity);
    }

    private Double calculatePriceAfterDiscountForProduct(Long discountId, double price) {
        DiscountResponse discountResponse = mapper.getDiscountFromProduct(discountId);
        if (discountResponse != null) { // If I have Discount object ...
            if (discountResponse.getIsActive() && discountResponse.getDiscountPercent() > 0) { // If I have Discount object ==> isActive == true And discountPercent > 0 (note: discountPercent > 0 it's like isActive false but to handle any case)...
                return calculatePriceAfterDiscount(price, discountResponse.getDiscountPercent());
            }
            return price; // else get the price as it is (Because no discount for product)
        }
        throw new RuntimeException("Discount Not Found With ID = " + discountId);
    }

    private double calculatePriceAfterDiscount(double price, double discountPercent) {
        return price * (discountPercent / 100);
    }

    @Override
    public void deleteProduct(Long id) {
        ProductEntity productToDelete = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + " ID Not Found "));
        productRepository.delete(productToDelete);
        mapper.decreaseInventoryQuantity(productToDelete.getInventoryId());
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
        existingProduct.setName(updateProductRequest.getName());
        existingProduct.setDescription(updateProductRequest.getDescription());
        existingProduct.setCategoryId(updateProductRequest.getCategoryId());
        existingProduct.setModifiedAt(LocalDateTime.now());
        // check if InventoryId changed to avoid more requests we don't need it if there is no changes
        if (existingProduct.getInventoryId().longValue() != updateProductRequest.getInventoryId().longValue()) {
            // decreaseInventoryQuantity for this existingProduct.InventoryId and increaseInventoryQuantity for new Product(updateProduct).InventoryId
            mapper.decreaseInventoryQuantity(existingProduct.getInventoryId());
            existingProduct.setInventoryId(updateProductRequest.getInventoryId());
            mapper.increaseInventoryQuantity(updateProductRequest.getInventoryId());
        }
        // check if DiscountId or Price changed to avoid more requests we don't need it if there is no changes
        if (existingProduct.getDiscountId().longValue() != updateProductRequest.getDiscountId().longValue()
                || existingProduct.getPrice().doubleValue() != updateProductRequest.getPrice().doubleValue()
        ) {
            existingProduct.setPrice(updateProductRequest.getPrice());
            existingProduct.setDiscountId(updateProductRequest.getDiscountId());
            Double salePrice = calculatePriceAfterDiscountForProduct(updateProductRequest.getDiscountId(), updateProductRequest.getPrice());
            existingProduct.setSalePrice(salePrice);
        }

    }


}