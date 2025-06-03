package com.juniorsfredo.xtreme_management_api.api.dto.user;

import com.juniorsfredo.xtreme_management_api.api.dto.references.RoleReferenceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponseDTO {

    private Long id;
    private String email;
    private String name;
    private String cpf;
    private Double height;
    private Double weight;
    private LocalDate birthDate;
    private String address;
    private String profileImgUrl;
    private List<RoleReferenceDTO> roles;
}
