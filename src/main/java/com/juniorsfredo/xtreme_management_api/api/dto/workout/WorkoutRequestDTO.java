package com.juniorsfredo.xtreme_management_api.api.dto.workout;

import com.juniorsfredo.xtreme_management_api.api.dto.exercise.ExerciseRequestDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.references.MemberReferenceDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.references.PersonalReferenceDTO;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.Muscle;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WorkoutRequestDTO {

    private Muscle name;

    private MemberReferenceDTO member;

    private PersonalReferenceDTO personal;

    private List<ExerciseRequestDTO> exercises = new ArrayList<>();
}
