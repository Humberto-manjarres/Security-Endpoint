package com.hmg.springsecurity.service;

import com.hmg.springsecurity.config.JwtService;
import com.hmg.springsecurity.controller.models.AuthResponse;
import com.hmg.springsecurity.controller.models.AuthenticationRequest;
import com.hmg.springsecurity.controller.models.RegisterRequest;
import com.hmg.springsecurity.entity.Role;
import com.hmg.springsecurity.entity.User;
import com.hmg.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);/*TODO: guardamos el usuario en BD*/
        var jwtToken = jwtService.generateToken(user); /*TODO: generar el Jwt token*/
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthResponse authenticate(AuthenticationRequest request) {

        /*TODO: se realiza la autenticación*/
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        /*TODO: verificamos que exista el usuario*/
        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user); /*TODO: generar el Jwt token*/
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
