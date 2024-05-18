package com.samara.user.service.config;

import com.samara.user.service.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Order 3

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static String USER_NAME;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // 1- Check JWT Token
        final String authHeader = request.getHeader("Authorization"); // we need to pass authToken to the Header
        final String jwt;
        final String username; // Or email if the email is the username

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2- extract the token from auth header
        jwt = authHeader.substring(7);

        username = jwtService.extractUsername(jwt); // extract username from JWT Token (We need class to create (JwtService) to implement this)
        USER_NAME = username;
        // validate Jwt
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // SecurityContextHolder.getContext().getAuthentication() == null That's mean the user is not yet authenticate
            // Because if he is authenticated I don't need to do all the validate process again

            // check if the user already exist in my DB
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // check if the token still valid
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // because we don't have credentials
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // update SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
            filterChain.doFilter(request, response);
        }
    }
}
