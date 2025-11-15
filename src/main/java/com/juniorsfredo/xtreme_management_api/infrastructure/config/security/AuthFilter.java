package com.juniorsfredo.xtreme_management_api.infrastructure.config.security;

import com.juniorsfredo.xtreme_management_api.domain.exceptions.UserNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.models.User;
import com.juniorsfredo.xtreme_management_api.domain.repositories.jpa.UserRepository;
import com.juniorsfredo.xtreme_management_api.domain.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
public class AuthFilter extends OncePerRequestFilter {

    private HandlerExceptionResolver exceptionResolver;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    @Autowired
    public AuthFilter(JwtService jwtService,
                      UserRepository userRepository,
                      @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver)
    {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
    {
        try {
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
        } catch (Exception ex) {
            ex.printStackTrace();
            exceptionResolver.resolveException(request, response, null, ex);
        }
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return jwtService.extractJwtTokenFromHeader(authorizationHeader);
        }
        return null;
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        return !Arrays.asList(SecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);
    }
}