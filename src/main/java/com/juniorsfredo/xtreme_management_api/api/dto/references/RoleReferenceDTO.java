package com.juniorsfredo.xtreme_management_api.api.dto.references;

import com.juniorsfredo.xtreme_management_api.domain.models.enums.RoleName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleReferenceDTO {

    @NotNull
    @Positive
    private Long id;

    private RoleName name;
}
