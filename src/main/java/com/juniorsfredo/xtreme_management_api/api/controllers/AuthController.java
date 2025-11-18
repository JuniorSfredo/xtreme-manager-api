package com.juniorsfredo.xtreme_management_api.api.controllers;

import com.juniorsfredo.xtreme_management_api.api.dto.auth.AuthenticatedResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.UserLoginDTO;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.BusinessException;
import com.juniorsfredo.xtreme_management_api.domain.models.User;
import com.juniorsfredo.xtreme_management_api.domain.ports.EmailService;
import com.juniorsfredo.xtreme_management_api.domain.services.AuthService;
import com.juniorsfredo.xtreme_management_api.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    // private final EmailService emailService;

    @Autowired
    public AuthController(AuthService authService,
                          UserService userService
                          //EmailService emailService
    ) {
        this.authService = authService;
        this.userService = userService;
        // this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticatedResponseDTO> login(@RequestBody UserLoginDTO userBody) {
        AuthenticatedResponseDTO authenticatedResponseDTO = authService.login(userBody);
        return ResponseEntity.ok(authenticatedResponseDTO);
    }

    @PostMapping("/send-code-reset-password")
    public ResponseEntity<Void> sendEmailCodeToChangePassword(@RequestParam String email) {

        if (email.isEmpty()) {
            throw new BusinessException("Email is empty");
        }

        User user = userService.getUserByEmail(email);

        System.out.println(user.getId());
        String code = authService.generateCodeToChangePassword();

                authService.saveCode(user.getId(), code);

//        CompletableFuture<Void> sendEmailFuture = CompletableFuture.runAsync(() ->
//                emailService.sendEmail(email, "Código para redefinir sua senha", code)
//        );

        authService.saveSessionToken(user.getId(), email);

        return ResponseEntity.ok().build();
    }
}
