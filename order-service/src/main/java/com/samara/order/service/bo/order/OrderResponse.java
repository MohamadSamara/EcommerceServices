package com.samara.order.service.bo.order;

import com.samara.order.service.bo.orderItem.OrderItemResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private List<OrderItemResponse> orderItem;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
}