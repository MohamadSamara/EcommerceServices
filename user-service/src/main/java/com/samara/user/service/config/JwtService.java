package com.samara.user.service.config;

import com.samara.user.service.bo.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

// order 4
@Service
public class JwtService {

    private static final String SECRET_KEY = "5131ae6d5ed68eec897c6f2e3681097b373f4059c26a93b00d7ed1530a651fc5";

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    // Claims came from jsonwebtoken dependency
    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public String generateToken(
            Map<String, Object> extractClaims,
            String subject
    ) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis())) // check if the token is still valid
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24))) // How Long the token should be valid
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact(); // will generate and return the token
    }

    // generate token from only userDetails
    public String generateToken(CustomUserDetails userDetails) {
        return generateToken(userDetails.getClaims(), userDetails.getUsername());
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public boolean validateToken(String token) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
