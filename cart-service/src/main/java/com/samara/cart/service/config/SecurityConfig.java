package com.samara.cart.service.config;

import com.samara.shared.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String SWAGGER_UI_HTML_PAGE = "/swagger-ui.html";
    private static final String SWAGGER_UI_PATH = "/swagger-ui/**";
    private static final String DOCS_PATH = "/api-docs/**";

    @Value("${signing.key}")
    private String signingKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SWAGGER_UI_PATH).permitAll()
                        .requestMatchers(SWAGGER_UI_HTML_PAGE).permitAll()
                        .requestMatchers(DOCS_PATH).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(signingKey), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}