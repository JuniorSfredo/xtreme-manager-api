package com.juniorsfredo.xtreme_management_api.api.controllers;

import com.juniorsfredo.xtreme_management_api.api.dto.auth.AccessTokenDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.UserLoginDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.UserRegisterDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.user.UserResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.Role;
import com.juniorsfredo.xtreme_management_api.domain.repositories.UserRepository;
import com.juniorsfredo.xtreme_management_api.domain.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
   }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenDTO> login(@RequestBody UserLoginDTO userBody) {
        AccessTokenDTO accessTokenDTO = authService.login(userBody);
        return ResponseEntity.ok(accessTokenDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRegisterDTO userBody, UriComponentsBuilder builder) throws Exception {
        UserResponseDTO userResponseDTO = authService.register(userBody);
        URI uri = builder.path("/users/{id}").buildAndExpand(userResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(userResponseDTO);
    }
}
