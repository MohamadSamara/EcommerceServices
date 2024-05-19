package com.samara.order.service.bo.order;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CreateOrderRequest {
    private Long userId;
    private List<Long> productList;
    private Double totalPrice;
    private LocalDateTime createdAt;
}