package com.samara.cart.service.bo.cart;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UpdateCartRequest {
    private Integer quantity;
    private LocalDateTime modifiedAt;
}