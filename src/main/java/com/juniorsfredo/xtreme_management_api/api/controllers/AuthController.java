package com.juniorsfredo.xtreme_management_api.api.controllers;

import com.juniorsfredo.xtreme_management_api.api.dto.auth.AuthenticatedResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.AuthenticatedUserResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.UserLoginDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.UserRegisterDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.user.UserResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.services.AuthService;
import com.juniorsfredo.xtreme_management_api.domain.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticatedResponseDTO> login(@RequestBody UserLoginDTO userBody) {
        System.out.println(userBody.email());
        AuthenticatedResponseDTO authenticatedResponseDTO = authService.login(userBody);
        return ResponseEntity.ok(authenticatedResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRegisterDTO userBody,
                                                    UriComponentsBuilder builder) throws Exception
    {
        UserResponseDTO userResponseDTO = authService.register(userBody);
        URI uri = builder.path("/users/{id}").buildAndExpand(userResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(userResponseDTO);
    }

    @GetMapping("/user/get-by-token")
    public ResponseEntity<AuthenticatedUserResponseDTO> getAuthenticatedUserByToken(
            @RequestHeader("Authorization") String authorizationHeader)
    {
        String token = jwtService.extractJwtTokenFromHeader(authorizationHeader);

        AuthenticatedUserResponseDTO response = authService.getUserByToken(token);
        return ResponseEntity.ok(response);
    }
}
