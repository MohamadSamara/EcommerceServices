package com.samara.apigateway.filter;

import com.samara.apigateway.util.JwtService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidation routeValidation;
    private final JwtService jwtUtil;

    public AuthenticationFilter(RouteValidation routeValidation, JwtService jwtUtil) {
        super(Config.class);
        this.routeValidation = routeValidation;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (
                (exchange, chain) -> {
                    if (routeValidation.isSecured.test(exchange.getRequest())) {
                        // check if the header contains token or not
                        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                            throw new RuntimeException("Missing AUTHORIZATION Header");
                        }

                        String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                        if (authHeader != null && authHeader.startsWith("Bearer ")) {
                            authHeader = authHeader.substring(7);
                        }

                        try {
                            // We Can Use WebClient To Call Auth Service But This Not A Good Practice (Will Cause Security Issue)
                            jwtUtil.validateToken(authHeader);
                        } catch (Exception e) {
                            throw new RuntimeException("Invalid Access ...");
                        }
                    }
                    return chain.filter(exchange);
                });
    }

    public static class Config {

    }
}
