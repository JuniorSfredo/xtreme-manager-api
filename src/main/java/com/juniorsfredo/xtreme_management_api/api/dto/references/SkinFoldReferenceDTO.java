package com.juniorsfredo.xtreme_management_api.api.dto.references;

import com.juniorsfredo.xtreme_management_api.domain.models.enums.SkinFoldType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkinFoldReferenceDTO {
    private Long id;
    private SkinFoldType fold;
    private Double mm;
}
