package com.juniorsfredo.xtreme_management_api.api.dto.user;

import com.juniorsfredo.xtreme_management_api.domain.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String email;
    private String name;
    private List<Role> roles;
}
