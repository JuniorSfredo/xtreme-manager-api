package com.juniorsfredo.xtreme_management_api.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatedUserResponseDTO {
    private Long id;
    private String name;
    private String email;
}
