package com.samara.user.service.service;

import com.samara.user.service.bo.AuthenticationRequest;
import com.samara.user.service.bo.AuthenticationResponse;
import com.samara.user.service.bo.RegisterRequest;

public interface AuthenticationService {

    String register(RegisterRequest registerRequest);

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    boolean validateToken(String token);
}
