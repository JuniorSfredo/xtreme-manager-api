package com.juniorsfredo.xtreme_management_api.api.dto.auth;

import com.juniorsfredo.xtreme_management_api.api.dto.user.UserDetailsResponseDTO;

public record AuthenticatedResponseDTO(String accessToken, UserDetailsResponseDTO user) {
}