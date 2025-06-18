package com.juniorsfredo.xtreme_management_api.infrastructure.config.security;

import com.juniorsfredo.xtreme_management_api.api.cors.CorsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/auth/login",
            "/users/{id}",
            "/assessments/{id}",
            "/assessments/users/{userId}/latest-two",
            "/auth/user/get-by-token",
    };

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
    };

    public static final String[] ENDPOINTS_MEMBER = {

    };

    public static final String[] ENDPOINTS_PERSONAL = {
    };

    public static final String[] ENDPOINTS_ADMIN = {
    };

    AuthFilter authFilter;

    private final CorsConfig corsConfig;


    @Autowired
    public SecurityConfig(AuthFilter authFilter, CorsConfig corsConfig) {
        this.authFilter = authFilter;
        this.corsConfig = corsConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthFilter authFilter, CorsConfigurationSource corsConfigurationSource) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                        .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMIN")
                        .requestMatchers(ENDPOINTS_PERSONAL).hasRole("PERSONAL")
                        .requestMatchers(ENDPOINTS_MEMBER).hasRole("MEMBER")
                        .anyRequest().denyAll()
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // TO DO: ENCRYPT PWD
    }
}
