package com.samara.order.service.bo.orderItem;

import com.samara.order.service.bo.product.ProductResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {
    private Long id;
    private ProductResponse productId;
    private Integer quantity;
    private Double totalPrice;
}
