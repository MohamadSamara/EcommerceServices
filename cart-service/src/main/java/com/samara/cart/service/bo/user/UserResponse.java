package com.samara.cart.service.bo.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Long telephone;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String role;
}
