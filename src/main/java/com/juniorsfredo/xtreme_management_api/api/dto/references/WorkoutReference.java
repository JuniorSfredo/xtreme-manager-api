package com.juniorsfredo.xtreme_management_api.api.dto.references;

import com.juniorsfredo.xtreme_management_api.domain.models.enums.Muscle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutReference {
    private Long id;
    private Muscle name;
}
