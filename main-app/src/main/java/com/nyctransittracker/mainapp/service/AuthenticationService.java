package com.nyctransittracker.mainapp.service;

import com.nyctransittracker.mainapp.dto.AuthenticationRequest;
import com.nyctransittracker.mainapp.dto.AuthenticationResponse;
import com.nyctransittracker.mainapp.dto.RegisterRequest;
import com.nyctransittracker.mainapp.dto.UserDto;
import com.nyctransittracker.mainapp.model.Role;
import com.nyctransittracker.mainapp.model.User;
import com.nyctransittracker.mainapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isEmpty()){
            User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

            repository.save(user);
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(UserDto.userMapper(user))
                .build();
        } else {
            return AuthenticationResponse.builder()
                .message("Email already taken.")
                .build();
        }
    }
    
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
             )
        );
        } catch (AuthenticationException e) {
            System.out.println(e);
            return AuthenticationResponse.builder()
                .message("Invalid login credentials.")
                .build();
        }

        User user = repository.findByEmail(request.getEmail()).get();
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
            .token(jwtToken)
            .user(UserDto.userMapper(user))
            .build();
    }
}
