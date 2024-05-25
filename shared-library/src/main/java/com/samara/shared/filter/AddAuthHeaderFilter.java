package com.samara.shared.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public class AddAuthHeaderFilter {

    public static ExchangeFilterFunction addAuthHeaderFilterFunction() {
        return (clientRequest, next) -> next.exchange(ClientRequest.from(clientRequest)
                .headers(headers -> headers.add("Authorization", getJwtFromContext()))
                .build());
    }

    private static String getJwtFromContext() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String) {
            return "Bearer " + authentication.getCredentials();
        }
        return null;
    }


}

