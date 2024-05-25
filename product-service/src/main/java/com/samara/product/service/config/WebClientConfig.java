package com.samara.product.service.config;

import com.samara.shared.filter.AddAuthHeaderFilter;
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
                .baseUrl("http://localhost:8081/api/v1/category")
                .filter(AddAuthHeaderFilter.addAuthHeaderFilterFunction())
                .build();
    }

    @Bean("inventoryWebClient")
    public WebClient inventoryWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8082/api/v1/inventory")
                .filter(AddAuthHeaderFilter.addAuthHeaderFilterFunction())
                .build();
    }

    @Bean("discountWebClient")
    public WebClient discountWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8083/api/v1/discount")
                .filter(AddAuthHeaderFilter.addAuthHeaderFilterFunction())
                .build();
    }

}