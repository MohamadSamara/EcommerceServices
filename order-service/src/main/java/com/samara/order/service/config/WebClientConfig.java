package com.samara.order.service.config;

import com.samara.shared.filter.AddAuthHeaderFilter;
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
                .baseUrl("http://localhost:8088/api/v1/product")
                .filter(AddAuthHeaderFilter.addAuthHeaderFilterFunction())
                .build();
    }

    @Bean("cartWebClient")
    @LoadBalanced
    public WebClient cartWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8085/api/v1/cart")
                .filter(AddAuthHeaderFilter.addAuthHeaderFilterFunction())
                .build();
    }
}