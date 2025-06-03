package com.juniorsfredo.xtreme_management_api.api.dto.auth;

import com.juniorsfredo.xtreme_management_api.domain.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtUserData {
    private Long id;
    private String name;
    private String email;
    private List<Role> roles;
}
