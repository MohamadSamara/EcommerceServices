package com.samara.discount.service.service;

import com.samara.discount.service.bo.discount.CreateDiscountRequest;
import com.samara.discount.service.bo.discount.DiscountResponse;
import com.samara.discount.service.bo.discount.UpdateDiscountRequest;

public interface DiscountService {

    DiscountResponse discount(Long id);

    DiscountResponse addDiscount(CreateDiscountRequest discountRequest);

    DiscountResponse updateDiscount(UpdateDiscountRequest updateDiscountRequest, Long id);
}