package com.juniorsfredo.xtreme_management_api.api.controllers;

import com.juniorsfredo.xtreme_management_api.api.dto.auth.AuthenticatedResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.CodeResetPasswordRequestDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.ResetPasswordRequestDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.UserLoginDTO;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.BusinessException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.UserNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.models.User;
import com.juniorsfredo.xtreme_management_api.domain.services.EmailService;
import com.juniorsfredo.xtreme_management_api.domain.services.AuthService;
import com.juniorsfredo.xtreme_management_api.domain.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final EmailService emailService;

    @Autowired
    public AuthController(AuthService authService,
                          UserService userService,
                          EmailService emailService
    ) {
        this.authService = authService;
        this.userService = userService;
        this.emailService = emailService;

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticatedResponseDTO> login(@RequestBody UserLoginDTO userBody) {
        AuthenticatedResponseDTO authenticatedResponseDTO = authService.login(userBody);
        return ResponseEntity.ok(authenticatedResponseDTO);
    }

    @PostMapping("/send-code-reset-password")
    public ResponseEntity<Void> sendEmailCodeToChangePassword(@RequestParam String email) {

        if (email == null ||
            email.isBlank() ||
            !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")
        ) {
            throw new BusinessException("Email inválido");
        }

        Optional<User> userOpt = userService.findUserByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.ok().build();
        }

        User user = userOpt.get();

        String code = authService.generateCodeToChangePassword();

        try {
            emailService.sendEmail(
                    email,
                    "Seu código único para redefinir a senha.",
                    buildEmailSendCodeBody(code)
            );

            authService.saveCode(user.getId(), code);

            System.out.println(code);

        } catch (Exception e) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate-reset-code")
    public ResponseEntity<Void> validateResetCode(
            @Valid @RequestBody CodeResetPasswordRequestDTO codeResetRequestDTO
    ) {

        Optional<User> userOpt = userService.findUserByEmail(codeResetRequestDTO.getEmail());

        if (userOpt.isEmpty()) {
            throw new BusinessException("Invalid or expired code");
        }

        User user = userOpt.get();

        boolean isValid = authService.isValidCode(user.getId(), codeResetRequestDTO.getCode());

        if (!isValid) {
            throw new BusinessException("Invalid or expired code");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {

        String email = resetPasswordRequestDTO.getEmail();

        Optional<User> userOpt = userService.findUserByEmail(email);

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User user = userOpt.get();

        Boolean isValid = authService.isValidCode(user.getId(), resetPasswordRequestDTO.getCode());
        if (!isValid) {
            throw new BusinessException("Invalid or expired code, verify your email and try again!");
        }

        if (!user.getPassword().equals(resetPasswordRequestDTO.getNewPassword())) {
            String newPassword = authService.encodePassword(resetPasswordRequestDTO.getNewPassword());
            userService.changePassword(user.getId(), newPassword);
        }

        authService.invalidateCode(user.getId());

        CompletableFuture.runAsync(() -> {
            emailService.sendEmail(user.getEmail(), "Sua senha foi redefinida.", buildEmailUpdatePasswordBody());
        });

        return ResponseEntity.ok().build();
    }

    private String buildEmailSendCodeBody(String code) {
        return "Insira o código abaixo para redefinir sua senha! <br>" +
                "Seu código único: <b>" + code + "</b> <br><br>" +
                "<b>IMPORTANTE:</b> caso não tenha feito o pedido para redefinição de senha, ignore essa mensagem.";
    }

    private String buildEmailUpdatePasswordBody() {
        return "Sua senha foi atualizada! <br>" +
                "<b>IMPORTANTE:</b> caso você não tenha feito a atualização, entre em contato com o suporte imediatamente" +
                "email@support.com";
    }
}
