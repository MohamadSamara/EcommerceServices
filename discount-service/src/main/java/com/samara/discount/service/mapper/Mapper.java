package com.samara.discount.service.mapper;

import com.samara.discount.service.bo.discount.CreateDiscountRequest;
import com.samara.discount.service.bo.discount.DiscountResponse;
import com.samara.discount.service.model.DiscountEntity;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

//    public DiscountEntity discountResponseToDiscountEntity(DiscountResponse discountResponse){
//        return DiscountEntity.builder()
//                .id(discountResponse.getId())
//                .name(discountResponse.getName())
//                .description(discountResponse.getDescription())
//                .isActive(discountResponse.getIsActive())
//                .discountPercent(discountResponse.getDiscountPercent())
//                .createdAt(discountResponse.getCreatedAt())
//                .modifiedAt(discountResponse.getModifiedAt())
//                .build();
//    }

    public DiscountResponse DiscountEntityToDiscountResponse(DiscountEntity discountEntity){
        return DiscountResponse.builder()
                .id(discountEntity.getId())
                .name(discountEntity.getName())
                .description(discountEntity.getDescription())
                .isActive(discountEntity.getIsActive())
                .discountPercent(discountEntity.getDiscountPercent())
                .createdAt(discountEntity.getCreatedAt())
                .modifiedAt(discountEntity.getModifiedAt())
                .build();
    }

    public DiscountEntity createDiscountRequestToDiscountEntity(CreateDiscountRequest discountRequest){
        return DiscountEntity.builder()
                .name(discountRequest.getName())
                .description(discountRequest.getDescription())
                .isActive(discountRequest.getIsActive())
                .discountPercent(discountRequest.getDiscountPercent())
                .createdAt(discountRequest.getCreatedAt())
                .build();
    }

}
