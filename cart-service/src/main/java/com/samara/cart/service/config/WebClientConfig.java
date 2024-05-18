package com.samara.cart.service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean("productWebClient")
    @LoadBalanced
    public WebClient productWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080/api/v1/product")
                .build();
    }

    @Bean("userWebClient")
    @LoadBalanced
    public WebClient userWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8084/api/v1/user")
                .build();
    }
}