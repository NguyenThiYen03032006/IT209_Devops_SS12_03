package com.it211_ss18_04.controller;

import com.it211_ss18_04.dto.LoginRequest;
import com.it211_ss18_04.dto.LoginResponse;
import com.it211_ss18_04.security.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token =
                jwtUtil.generateToken(request.getUsername());

        return new LoginResponse(token);
    }
}
