package com.samara.shared.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private static final String SIGNING_KEY = "5131ae6d5ed68eec897c6f2e3681097b373f4059c26a93b00d7ed1530a651fc5";


    @Override
    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws IOException, ServletException {
        final String authHeader = request.getHeader("authorization");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.error("Invalid or missing Authorization header.");
                log.error("authHeader ==> {}", authHeader);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid or missing Authorization header.");
                throw new ServletException("An exception occurred (Invalid Token)");
            }
        }
        final String token = authHeader.substring(7);
        log.info("JWT Token: {}", token);
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.info("Claims: {}", claims);
            request.setAttribute("claims", claims);
            request.setAttribute("token", token);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            claims.getSubject(),
                            token,
                            null // Set authorities here if any
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private Key getSigningKey() {
        byte[] keyByte = Decoders.BASE64.decode(SIGNING_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}