package com.juniorsfredo.xtreme_management_api.infrastructure.config.security;

import com.juniorsfredo.xtreme_management_api.api.cors.CorsConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/auth/login",
            "/subscriptions/webhook",
            "/success.html"
    };

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/subscriptions",
            "/subscriptions/{subscriptionId}",
            "/subscriptions/users/{userId}",
            "/users/{id}",
            "/assessments/{id}",
            "/assessments/users/{userId}",
            "/assessments/users/{userId}/latest-three",
            "/auth/user/get-by-token",
            "/workouts/users/{userId}",
            "/workouts/{id}",
            "/register-workout",
            "/auth/logout",
            "/workouts/{personalId}/{memberId}/create-workout",
            "/members/{personalId}/register-member",
            "/users/{id}/streaks",
            "/register-workout/{userId}",
    };

    public static final String[] ENDPOINTS_MEMBER = {
    };

    public static final String[] ENDPOINTS_PERSONAL = {
        "/personals/{personalId}/new-member"
    };

    public static final String[] ENDPOINTS_ADMIN = {
    };

    private final CorsConfig corsConfig;


    private HandlerExceptionResolver exceptionResolver;

    @Autowired
    public SecurityConfig(CorsConfig corsConfig,
                          @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver)
    {
        this.corsConfig = corsConfig;
        this.exceptionResolver = exceptionResolver;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   AuthFilter authFilter,
                                                   CorsConfigurationSource corsConfigurationSource) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable) // WARN: DON'T PROD
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).authorizeHttpRequests(auth -> auth
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                        .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMIN")
                        .requestMatchers(ENDPOINTS_PERSONAL).hasRole("PERSONAL")
                        .requestMatchers(ENDPOINTS_MEMBER).hasRole("MEMBER")
                        .anyRequest().denyAll()
                )
                .exceptionHandling(customHandling -> {
                    customHandling.authenticationEntryPoint((request,
                                                             response,
                                                             authException) ->
                            exceptionResolver.resolveException(request, response, null, authException));

                    customHandling.accessDeniedHandler((request,
                                                        response,
                                                        accessDeniedException) ->
                            exceptionResolver.resolveException(request, response, null, accessDeniedException));
                })
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // TO DO: ENCRYPT PWD
    }
}
