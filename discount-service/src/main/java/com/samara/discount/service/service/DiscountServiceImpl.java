package com.samara.discount.service.service;

import com.samara.discount.service.bo.discount.CreateDiscountRequest;
import com.samara.discount.service.bo.discount.DiscountResponse;
import com.samara.discount.service.bo.discount.UpdateDiscountRequest;
import com.samara.discount.service.mapper.Mapper;
import com.samara.discount.service.model.DiscountEntity;
import com.samara.discount.service.repository.DiscountRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final Mapper mapper;

    public DiscountServiceImpl(DiscountRepository discountRepository, Mapper mapper) {
        this.discountRepository = discountRepository;
        this.mapper = mapper;
    }

    @Cacheable(value = "discountCache", key = "'discount:'+#id")
    @Override
    public DiscountResponse discount(Long id) {
        DiscountEntity discountEntity = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found with id = " + id));
        return mapper.DiscountEntityToDiscountResponse(discountEntity);
    }

    @CacheEvict(value = "discountCache", allEntries = true)
    @Override
    public DiscountResponse addDiscount(CreateDiscountRequest discountRequest) {
        discountRequest.setCreatedAt(LocalDateTime.now());
        DiscountEntity discountEntity = mapper.createDiscountRequestToDiscountEntity(discountRequest);
        discountRepository.save(discountEntity);
        return mapper.DiscountEntityToDiscountResponse(discountEntity);
    }

    @CacheEvict(value = "discountCache", key = "'discount:'+#id", allEntries = true)
    @Override
    public DiscountResponse updateDiscount(UpdateDiscountRequest updateDiscountRequest, Long id) {
        DiscountEntity existingEntity = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found with id => " + id));
        updateDiscountHelper(existingEntity, updateDiscountRequest);
        discountRepository.save(existingEntity);
        return mapper.DiscountEntityToDiscountResponse(existingEntity);
    }

    private void updateDiscountHelper(DiscountEntity existingEntity, UpdateDiscountRequest updateDiscountRequest) {
        existingEntity.setName(updateDiscountRequest.getName());
        existingEntity.setDescription(updateDiscountRequest.getDescription());
        existingEntity.setDiscountPercent(updateDiscountRequest.getDiscountPercent());
        existingEntity.setIsActive(updateDiscountRequest.getIsActive());
        existingEntity.setModifiedAt(LocalDateTime.now());
    }

    @CacheEvict(value = "discountCache", allEntries = true)
    @Override
    public String deleteDiscount(Long id) {
        DiscountEntity discountEntity = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found with id => " + id));
        discountRepository.delete(discountEntity);
        return "Discount Deleted Successfully";
    }

}