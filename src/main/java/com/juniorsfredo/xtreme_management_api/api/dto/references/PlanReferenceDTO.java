package com.juniorsfredo.xtreme_management_api.api.dto.references;

import com.juniorsfredo.xtreme_management_api.domain.models.enums.PlanTypes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanReferenceDTO {

    @NotBlank
    private Long id;

    @NotNull
    private PlanTypes plan;
}
