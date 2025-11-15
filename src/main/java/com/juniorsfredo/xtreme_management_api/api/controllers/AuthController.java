package com.juniorsfredo.xtreme_management_api.api.controllers;

import com.juniorsfredo.xtreme_management_api.api.dto.auth.AuthenticatedResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.UserLoginDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.user.UserPasswordDTO;
import com.juniorsfredo.xtreme_management_api.domain.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticatedResponseDTO> login(@RequestBody UserLoginDTO userBody) {
        AuthenticatedResponseDTO authenticatedResponseDTO = authService.login(userBody);
        return ResponseEntity.ok(authenticatedResponseDTO);
    }

    @PostMapping("/users/{userId}/send-code-reset-password")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<Void> sendCodeToChangePassword(@PathVariable Long userId,
                                                         @RequestBody UserPasswordDTO userPassword) {
        String code = authService.generateCodeToChangePassword();
        // enviar email
        System.out.println(code);
        // salvar redis
        return ResponseEntity.ok().build();
    }
}
