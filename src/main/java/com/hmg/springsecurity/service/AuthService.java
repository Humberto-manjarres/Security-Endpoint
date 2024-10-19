package com.hmg.springsecurity.service;

import com.hmg.springsecurity.controller.models.AuthResponse;
import com.hmg.springsecurity.controller.models.AuthenticationRequest;
import com.hmg.springsecurity.controller.models.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse authenticate(AuthenticationRequest authenticationRequest);

}
