package com.samara.user.service.service;

import com.samara.user.service.bo.AuthenticationRequest;
import com.samara.user.service.bo.AuthenticationResponse;
import com.samara.user.service.bo.CustomUserDetails;
import com.samara.user.service.bo.RegisterRequest;
import com.samara.user.service.config.JwtService;
import com.samara.user.service.model.Role;
import com.samara.user.service.model.UserEntity;
import com.samara.user.service.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;


    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public String register(RegisterRequest registerRequest) {
        UserEntity user = UserEntity.builder()
                .username(registerRequest.getUsername())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .telephone(registerRequest.getTelephone())
                .createdAt(LocalDateTime.now())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return "User Registered Successfully";
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        requiredNonNUll(authenticationRequest.getUsername(), "username");
        requiredNonNUll(authenticationRequest.getPassword(), "password");

        String username = authenticationRequest.getUsername().toLowerCase();
        String password = authenticationRequest.getPassword();

        authenticate(username, password);

        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String jwtToken = jwtService.generateToken(userDetails);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect password");
        } catch (AuthenticationServiceException e) {
            throw new UsernameNotFoundException("Incorrect Username");
        }
    }

    private void requiredNonNUll(Object obj, String name) {
        if (obj == null || obj.toString().isEmpty()) {
            throw new RuntimeException(name + " can't be empty!");
        }

    }

}
