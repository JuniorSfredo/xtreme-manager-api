package com.juniorsfredo.xtreme_management_api.api.dto.workout;

import com.juniorsfredo.xtreme_management_api.api.dto.references.MemberReferenceDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.references.PersonalReferenceDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.Muscle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutResponseDTO {

    private Long id;

    private Muscle name;

    private MemberReferenceDTO member;

    private PersonalReferenceDTO personal;
}
