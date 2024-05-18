package com.samara.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidation {
    // List of what we want to call it without AuthenticationFilter
    public static final List<String> openAiEndpoints = List.of(
//            "/api/v1/category",
//            "/api/v1/discount",
//            "/api/v1/inventory",
//            "/api/v1/product",
//            "/api/v1/cart",
            "/api/v1/auth/register",
            "/api/v1/auth/authenticate"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openAiEndpoints
                    .stream()
                    .noneMatch(
                            uri -> request.getURI().getPath().contains(uri)
                    );

}
