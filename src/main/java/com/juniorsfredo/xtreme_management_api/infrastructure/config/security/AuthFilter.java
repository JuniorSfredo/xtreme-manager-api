package com.juniorsfredo.xtreme_management_api.infrastructure.config.security;

import com.juniorsfredo.xtreme_management_api.domain.exceptions.UserNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.models.User;
import com.juniorsfredo.xtreme_management_api.domain.repositories.UserRepository;
import com.juniorsfredo.xtreme_management_api.domain.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    @Autowired
    public AuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request);
            if (token != null) {
                String subject = jwtService.validateToken(token);
                Optional<User> userOptional = userRepository.findByEmail(subject);
                if (userOptional.isEmpty())
                    throw new UserNotFoundException("User not found with email: " + subject);

                User user = userOptional.get();

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        return !Arrays.asList(SecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);
    }
}