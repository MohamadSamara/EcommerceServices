package com.samara.product.service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean("categoryWebClient")
    @LoadBalanced
    public WebClient categoryWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081/")
                .build();
    }

    @Bean("inventoryWebClient")
    public WebClient inventoryWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8082/")
                .build();
    }

    @Bean("discountWebClient")
    public WebClient discountWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8083/")
                .build();
    }

}