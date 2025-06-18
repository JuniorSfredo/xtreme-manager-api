package com.juniorsfredo.xtreme_management_api.domain.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.InvalidAuthorizationHeaderException;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.InvalidCredentialsException;
import com.juniorsfredo.xtreme_management_api.domain.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
public class JwtService {

    private static final String SECRET_KEY = "4Z^XrroxR@dWxqf$mTTKwW$!@#qGr4P";

    private static final String ISSUER = "xtreme-management-api";

    public String generateToken(User userData) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            List<String> roleNames = userData.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            return JWT.create()
                    .withIssuer(ISSUER)
                    .withExpiresAt(expirationDate())
                    .withSubject(userData.getEmail())
                    .withClaim("id", userData.getId())
                    .withClaim("roles", roleNames)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token inválido ou expirado.");
        }
    }

    public String extractJwtTokenFromHeader(String authorizationHeader) {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.replace("Bearer ", "").trim();
            if (token.isEmpty()) throw new InvalidAuthorizationHeaderException("Token is empty.");
            return token;
        }

        throw new InvalidAuthorizationHeaderException("Authorization header is missing or invalid.");
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
