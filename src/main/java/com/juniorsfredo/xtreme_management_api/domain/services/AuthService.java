package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.api.assembler.AuthAssembler;
import com.juniorsfredo.xtreme_management_api.api.assembler.RoleAssembler;
import com.juniorsfredo.xtreme_management_api.api.assembler.UserAssembler;
import com.juniorsfredo.xtreme_management_api.api.dto.auth.*;
import com.juniorsfredo.xtreme_management_api.api.dto.user.UserDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.user.UserResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.InvalidCredentialsException;
import com.juniorsfredo.xtreme_management_api.domain.models.Role;
import com.juniorsfredo.xtreme_management_api.domain.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AuthService {

    private final AuthenticationManager authManager;

    private final JwtService jwtService;

    private final UserAssembler userAssembler;

    @Autowired
    public AuthService(AuthenticationConfiguration config,
                       JwtService jwtService,
                       UserAssembler userAssembler
    ) throws Exception {
        this.authManager = config.getAuthenticationManager();
        this.jwtService = jwtService;
        this.userAssembler = userAssembler;
    }

    public AuthenticatedResponseDTO login(UserLoginDTO userBody)  {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userBody.email(), userBody.password());

            Authentication authentication = authManager.authenticate(usernamePasswordAuthenticationToken);

            User user = (User) authentication.getPrincipal();

            UserDetailsResponseDTO authenticatedUser = userAssembler.toUserDetailsResponseDTO(user);

            String token = jwtService.generateToken(user);
            return new AuthenticatedResponseDTO(token, authenticatedUser);
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid credentials. Verify and try again.");
        }
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public String generateCodeToChangePassword() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
