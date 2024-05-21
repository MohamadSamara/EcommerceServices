package com.samara.user.service.bo;


import com.samara.user.service.model.Role;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Builder
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String telephone;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    // For getUsername and  getPassword We can remove them because in lombok(@Data) we have already getter and setter for them (in case we have field "username". "password")

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Map<String, Object> getClaims() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", this.id);
        claims.put("username", this.username);
        claims.put("firstName", this.firstName);
        claims.put("lastName", this.lastName);
        claims.put("telephone", this.telephone);
        claims.put("role", role);
//        claims.put("createdAt", this.createdAt); // Because its localDateTime it will cause error when we want to generate a token, so we should map it maybe using ObjectMapper (The Error : com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: io.jsonwebtoken.impl.DefaultClaims["createdAt"]))
        return claims;
    }

}