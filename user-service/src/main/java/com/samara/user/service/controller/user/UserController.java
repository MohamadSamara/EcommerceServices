package com.samara.user.service.controller.user;

import com.samara.user.service.bo.CustomUserDetails;
import com.samara.user.service.service.CustomUserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final CustomUserDetailsService userDetailsService;

    public UserController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/{username}")
    public CustomUserDetails user(@PathVariable String username) {
        return userDetailsService.loadUserByUsername(username);
    }

}