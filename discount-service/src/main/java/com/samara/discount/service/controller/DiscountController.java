package com.samara.discount.service.controller;

import com.samara.discount.service.bo.discount.CreateDiscountRequest;
import com.samara.discount.service.bo.discount.DiscountResponse;
import com.samara.discount.service.bo.discount.UpdateDiscountRequest;
import com.samara.discount.service.service.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discount")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountResponse> discount(@PathVariable Long id) {
        return ResponseEntity.ok(discountService.discount(id));
    }

    @PostMapping("/add")
    public ResponseEntity<DiscountResponse> addDiscount(@RequestBody CreateDiscountRequest discountRequest) {
        return new ResponseEntity<>(discountService.addDiscount(discountRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DiscountResponse> updateDiscount(@RequestBody UpdateDiscountRequest updateDiscountRequest, @PathVariable Long id) {
        return new ResponseEntity<>(discountService.updateDiscount(updateDiscountRequest, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDiscount(@PathVariable Long id) {
        return new ResponseEntity<>(discountService.deleteDiscount(id), HttpStatus.ACCEPTED);
    }
}