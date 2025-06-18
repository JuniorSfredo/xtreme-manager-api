package com.juniorsfredo.xtreme_management_api.api.dto.auth;

public record AuthenticatedResponseDTO(String accessToken, AuthenticatedUserResponseDTO user) {
}